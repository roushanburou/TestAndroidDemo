package car.bkrc.com.car2018;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;

import android.content.res.Configuration;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.bkrc.camera.XcApplication;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText device_edit = null;
    private EditText login_edit = null;
    private EditText passwd_edit = null;

    private Button bt_connect = null;
    private CheckBox rememberbox = null;

    private ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        // 判断是否是平板
        if (isPad(this))
            setContentView(R.layout.activity_login);
        else
            setContentView(R.layout.activity_login_mobilephone);

        //改变屏幕显示的方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        findViews();  //控件初始化
        cameraInit();//摄像头初始化
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void findViews() {
        device_edit = (EditText) findViewById(R.id.deviceid);
        login_edit = (EditText) findViewById(R.id.loginname);
        passwd_edit = (EditText) findViewById(R.id.loginpasswd);
        Button bt_reset = (Button) findViewById(R.id.reset);
        bt_connect = (Button) findViewById(R.id.connect);
        rememberbox = (CheckBox) findViewById(R.id.remember);
        rememberbox.setChecked(false);
        Switch transmitswitch = (Switch) findViewById(R.id.transmit_way);

        bt_reset.setOnClickListener(this);
        bt_connect.setOnClickListener(this);
        transmitswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton Button, boolean isChecked) {
                if (isChecked) {
                    XcApplication.isserial = XcApplication.Mode.USB_SERIAL;
                    Button.setText("使用usb转uart");
                    Toast.makeText(LoginActivity.this, "请务必确保使用A72平板通过串口接入大赛小车", Toast.LENGTH_SHORT).show();
                } else {
                    XcApplication.isserial = XcApplication.Mode.SOCKET;
                    Button.setText("使用socket");
                    Toast.makeText(LoginActivity.this, "请务必确保使用WiFi接入大赛小车", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.reset) {
            device_edit.setText("");
            login_edit.setText("");
            passwd_edit.setText("");
            rememberbox.setChecked(false);
        } else if (view.equals(bt_connect)) {

            dialog = new ProgressDialog(this);
            dialog.setMessage("小车正在疯狂加载中...");
            dialog.show();
            if (XcApplication.isserial == XcApplication.Mode.SOCKET) {
                //1
                useNetwork();
            } else if (XcApplication.isserial != XcApplication.Mode.SOCKET) {

                useUart();
            }
        }
    }

    private void useUart() {
        // 搜索摄像头然后启动摄像头
        search();
    }

    // 启动摄像头
    private void useUartCamera() {
        Intent ipintent = new Intent();
        //ComponentName的参数1:目标app的包名,参数2:目标app的Service完整类名
        ipintent.setComponent(new ComponentName("com.android.settings", "com.android.settings.ethernet.CameraInitService"));
        //设置要传送的数据
        ipintent.putExtra("purecameraip", FirstActivity.purecameraip);
        startService(ipintent);   //摄像头设为静态192.168.16.20时，可以不用发送
    }

    private void useNetwork() {
        //2.
        if (wifiInit()) {
            //WiFi初始化成功
            Toast.makeText(this, "WiFi初始化成功，正在启动摄像头", Toast.LENGTH_SHORT).show();
            search();
        } else {
            dialog.cancel();
            Toast.makeText(this, "请务必确保使用WiFi接入大赛小车", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean wifiInit() {
        // 得到服务器的IP地址
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        FirstActivity.IPCar = Formatter.formatIpAddress(dhcpInfo.gateway);
        if (FirstActivity.IPCar.equals("0.0.0.0")
                || FirstActivity.IPCar.equals("127.0.0.1")) return false;
        return true;
    }

    private void cameraInit() {
        //广播接收器注册
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(A_S);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    // 搜索摄像cameraIP
    private void search() {
        Intent intent = new Intent(LoginActivity.this, CameraSearchService.class);
        startService(intent);
    }

    // 广播名称
    public static final String A_S = "com.a_s";
    // 广播接收器接受SearchService搜索的摄像头IP地址加端口
    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context arg0, Intent arg1) {
            FirstActivity.IPCamera = arg1.getStringExtra("IP");
            FirstActivity.purecameraip = arg1.getStringExtra("pureip");
            Log.e("camera ip::", "  " + FirstActivity.IPCamera);

            // 如果是串口配置在这里提前启动摄像头驱动，否则是WiFi的话到下个界面再连接
            if (XcApplication.isserial != XcApplication.Mode.SOCKET) {
                useUartCamera();
            }

            // 收到广播信息后启动到下个界面
            startFirstActivity();
        }
    };

    private void startFirstActivity() {
        dialog.cancel();
        startActivity(new Intent(LoginActivity.this, FirstActivity.class));
        if (FirstActivity.IPCamera.equals("null:81")) {
            Toast.makeText(LoginActivity.this, "摄像头连接不上", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
        if (dialog != null) {
            dialog.cancel();
        }
    }


}
