package right.fragemt.recyclerview.data;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.List;

import car.bkrc.com.car2018.FirstActivity;
import car.bkrc.com.car2018.R;


public class ZigbeeAdapter extends RecyclerView.Adapter<ZigbeeAdapter.ViewHolder> {

    private List<Zigbee_Landmark> mZigbeeLandmarkList;
    static Context context = null;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View zigbeeView;
        ImageView zigbeeImage;
        TextView zigbeeName;


        public ViewHolder(View view) {
            super(view);
            zigbeeView = view;
            zigbeeImage = (ImageView) view.findViewById(R.id.landmark_image);
            zigbeeName = (TextView) view.findViewById(R.id.landmark_name);
        }
    }

    public ZigbeeAdapter(List<Zigbee_Landmark> zigbeeLandmarkList, Context context) {
        mZigbeeLandmarkList = zigbeeLandmarkList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zigbee_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.zigbeeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Zigbee_Landmark zigbeeLandmark = mZigbeeLandmarkList.get(position);
                zigbee_select(zigbeeLandmark);
                Toast.makeText(v.getContext(), "you clicked view " + zigbeeLandmark.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.zigbeeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Zigbee_Landmark zigbeeLandmark = mZigbeeLandmarkList.get(position);
                zigbee_select(zigbeeLandmark);
                Toast.makeText(v.getContext(), "you clicked image " + zigbeeLandmark.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    private void zigbee_select(Zigbee_Landmark zigbeeLandmark) {
        switch (zigbeeLandmark.getName()) {
            case "闸门":
                gateController();        // 闸门
                break;
            case "数码管":
                digital();                // 数码管
                break;
            case "语音播报":
                voiceController();    //语音播报
                break;
            case "无线充电":
                magnetic_suspension();  //磁悬浮
                break;
            case "TFT显示器":
                TFT_Control();
                break;
            case "智能交通灯":
                Traffic_Control();
                break;
            case "立体车库":
                stereo_garage_Control();
                break;
            default:
                break;
        }

    }

    private void stereo_garage_Control() {
        AlertDialog.Builder garage_builder = new AlertDialog.Builder(context);
        garage_builder.setTitle("立体车库");
        String[] ga = {"A", "B"};
        garage_builder.setSingleChoiceItems(ga, -1, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i) {
                    case 0: // A
                        stereo_garage_A();
                        break;
                    case 1: // B
                        stereo_garage_B();
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }

        });
        garage_builder.create().show();
    }

    private void Traffic_Control() {
        AlertDialog.Builder traffic_builder = new AlertDialog.Builder(context);
        traffic_builder.setTitle("智能交通灯");
        String[] ga = {"A", "B"};
        traffic_builder.setSingleChoiceItems(ga, -1, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i) {
                    case 0: // A
                        Traffic_light_A();
                        break;
                    case 1: // B
                        Traffic_light_B();
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }

        });
        traffic_builder.create().show();
    }

    private void TFT_Control() {
        AlertDialog.Builder tft_builder = new AlertDialog.Builder(context);
        tft_builder.setTitle("TFT显示器");
        String[] ga = {"A", "B"};
        tft_builder.setSingleChoiceItems(ga, -1, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i) {
                    case 0: // A
                        TFT_LCD_A();
                        break;
                    case 1: // B
                        TFT_LCD_B();
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }

        });
        tft_builder.create().show();
    }

    private void stereo_garage_B() {
        AlertDialog.Builder garage_builder = new AlertDialog.Builder(context);
        garage_builder.setTitle("立体车库B");
        String[] ga = {"到达第一层", "到达第二层", "到达第三层", "到达第四层", "请求返回车库位于第几层", "请求返回前后侧红外状态"};
        garage_builder.setSingleChoiceItems(ga, -1, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i) {
                    case 0:  //到达第一层
                        FirstActivity.Connect_Transport.garage_control(0x05,0x01, 0x01);
                        break;
                    case 1:  //到达第二层
                        FirstActivity.Connect_Transport.garage_control(0x05,0x01, 0x02);
                        break;
                    case 2:  //到达第三层
                        FirstActivity.Connect_Transport.garage_control(0x05,0x01, 0x03);
                        break;
                    case 3:  //到达第四层
                        FirstActivity.Connect_Transport.garage_control(0x05,0x01, 0x04);
                        break;
                    case 4:  //请求返回车库位于第几层
                        FirstActivity.Connect_Transport.garage_control(0x05,0x02, 0x01);
                        break;
                    case 5:  //请求返回前后侧红外状态
                        FirstActivity.Connect_Transport.garage_control(0x05,0x02, 0x02);
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }

        });
        garage_builder.create().show();
    }

    private void Traffic_light_B() {
        AlertDialog.Builder traffic_builder = new AlertDialog.Builder(context);
        traffic_builder.setTitle("智能交通灯B");
        String[] tr_light = {"进入识别模式", "识别结果为红色，请求确认", "识别结果为绿色，请求确认", "识别结果为黄色，请求确认"};
        traffic_builder.setSingleChoiceItems(tr_light, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i) {
                    case 0:
                        FirstActivity.Connect_Transport.traffic_control(0x0F,0x01, 0x00);
                        break;
                    case 1:
                        FirstActivity.Connect_Transport.traffic_control(0x0F,0x02, 0x01);
                        break;
                    case 2:
                        FirstActivity.Connect_Transport.traffic_control(0x0F,0x02, 0x02);
                        break;
                    case 3:
                        FirstActivity.Connect_Transport.traffic_control(0x0F,0x02, 0x03);
                        break;
                    default:
                        break;
                }
                dialog.dismiss();

            }
        });
        traffic_builder.create().show();
    }

    private void TFT_LCD_B() {
        AlertDialog.Builder TFTbuilder = new AlertDialog.Builder(context);
        TFTbuilder.setTitle("TFT显示器B");
        String[] TFTitem = {"图片显示模式", "车牌显示", "计时模式", "距离显示", "HEX显示模式"};
        TFTbuilder.setSingleChoiceItems(TFTitem, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                TFT_Image_B();
                                break;
                            case 1:
                                TFT_plate_number_B();
                                break;
                            case 2:
                                TFT_Timer_B();
                                break;
                            case 3:
                                Distance_B();
                                break;
                            case 4:
                                Hex_show_B();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
        TFTbuilder.create().show();
    }

    private void Hex_show_B() {
        AlertDialog.Builder TFT_Hex_builder = new AlertDialog.Builder(
                context);
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_hex, null);
        TFT_Hex_builder.setTitle("HEX显示模式");
        TFT_Hex_builder.setView(view);
        // 下拉列表
        final EditText editText1 = (EditText) view.findViewById(R.id.editText1);
        final EditText editText2 = (EditText) view.findViewById(R.id.editText2);
        final EditText editText3 = (EditText) view.findViewById(R.id.editText3);
        TFT_Hex_builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String ones = editText1.getText().toString();
                        String twos = editText2.getText().toString();
                        String threes = editText3.getText().toString();
                        // 显示数据，一个文本编译框最多两个数据显示数目管中两个数据
                        one = ones.equals("") ? 0x00 : Integer.parseInt(ones,16);
                        two = twos.equals("") ? 0x00 : Integer.parseInt(twos,16);
                        three = threes.equals("") ? 0x00 : Integer.parseInt(threes,16);
                        FirstActivity.Connect_Transport.TFT_LCD(0x08, 0x40, one, two, three);
                    }
                });
        TFT_Hex_builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });
        TFT_Hex_builder.create().show();
    }

    private void Distance_B() {
        AlertDialog.Builder TFT_Distance_builder = new AlertDialog.Builder(context);
        TFT_Distance_builder.setTitle("距离显示模式");
        String[] TFT_Image_item = {"400mm", "500mm", "600mm"};
        TFT_Distance_builder.setSingleChoiceItems(TFT_Image_item, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO 自动生成的方法存根
                if (which == 0) {
                    FirstActivity.Connect_Transport.TFT_LCD(0x08,0x50, 0x00, 0x04, 0x00);
                }
                if (which == 1) {
                    FirstActivity.Connect_Transport.TFT_LCD(0x08,0x50, 0x00, 0x05, 0x00);
                }
                if (which == 2) {
                    FirstActivity.Connect_Transport.TFT_LCD(0x08,0x50, 0x00, 0x06, 0x00);
                }
                dialog.cancel();
            }
        });
        TFT_Distance_builder.create().show();
    }

    private void TFT_plate_number_B() {
        AlertDialog.Builder TFT_plate_builder = new AlertDialog.Builder(context);
        TFT_plate_builder.setTitle("车牌显示模式");
        final String[] TFT_Image_item = {"Z799C4", "B554H1", "D888B8"};
        TFT_plate_builder.setSingleChoiceItems(TFT_Image_item, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO 自动生成的方法存根
                switch (which) {
                    case 0:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x20, 'Z', '7', '9');
                        FirstActivity.Connect_Transport.yanchi(500);
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x21, '9', 'C', '4');
                        break;
                    case 1:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x20, 'B', '5', '5');
                        FirstActivity.Connect_Transport.yanchi(500);
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x21, '4', 'H', '1');
                        break;
                    case 2:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x20, 'D', '8', '8');
                        FirstActivity.Connect_Transport.yanchi(500);
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x21, '8', 'B', '8');
                        break;
                }
                dialog.cancel();
            }
        });
        TFT_plate_builder.create().show();
    }

    private void TFT_Timer_B() {
        AlertDialog.Builder TFT_Iimer_builder = new AlertDialog.Builder(context);
        TFT_Iimer_builder.setTitle("计时模式");
        String[] TFT_Image_item = {"开始", "关闭", "停止"};
        TFT_Iimer_builder.setSingleChoiceItems(TFT_Image_item, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO 自动生成的方法存根
                switch (which) {
                    case 0:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x30, 0x01, 0x00, 0x00);
                        break;
                    case 1:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x30, 0x02, 0x00, 0x00);
                        break;
                    case 2:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x30, 0x00, 0x00, 0x00);
                        break;
                }
                dialog.cancel();
            }
        });
        TFT_Iimer_builder.create().show();
    }

    private void TFT_Image_B() {
        AlertDialog.Builder TFT_Image_builder = new AlertDialog.Builder(context);
        TFT_Image_builder.setTitle("图片显示模式");
        String[] TFT_Image_item = {"指定显示", "上翻一页", "下翻一页", "自动翻页"};
        TFT_Image_builder.setSingleChoiceItems(TFT_Image_item, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO 自动生成的方法存根
                switch (which) {
                    case 0:
                        TFT_B_show();
                        break;
                    case 1:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x10, 0x01, 0x00, 0x00);
                        break;
                    case 2:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x10, 0x02, 0x00, 0x00);
                        break;
                    case 3:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x10, 0x03, 0x00, 0x00);
                        break;
                }
                dialog.cancel();
            }
        });
        TFT_Image_builder.create().show();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Zigbee_Landmark zigbeeLandmark = mZigbeeLandmarkList.get(position);
        holder.zigbeeImage.setImageResource(zigbeeLandmark.getImageId());
        holder.zigbeeName.setText(zigbeeLandmark.getName());
    }

    @Override
    public int getItemCount() {
        return mZigbeeLandmarkList.size();
    }


    //智能交通灯标志物控制数据结构
    private void Traffic_light_A() {
        AlertDialog.Builder traffic_builder = new AlertDialog.Builder(context);
        traffic_builder.setTitle("智能交通灯A");
        String[] tr_light = {"进入识别模式", "识别结果为红色，请求确认", "识别结果为绿色，请求确认", "识别结果为黄色，请求确认"};
        traffic_builder.setSingleChoiceItems(tr_light, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i) {
                    case 0:
                        FirstActivity.Connect_Transport.traffic_control(0x0E,0x01, 0x00);
                        break;
                    case 1:
                        FirstActivity.Connect_Transport.traffic_control(0x0E,0x02, 0x01);
                        break;
                    case 2:
                        FirstActivity.Connect_Transport.traffic_control(0x0E,0x02, 0x02);
                        break;
                    case 3:
                        FirstActivity.Connect_Transport.traffic_control(0x0E,0x02, 0x03);
                        break;
                    default:
                        break;
                }
                dialog.dismiss();

            }
        });
        traffic_builder.create().show();
    }

    //立体车库
    private void stereo_garage_A() {
        AlertDialog.Builder garage_builder = new AlertDialog.Builder(context);
        garage_builder.setTitle("立体车库A");
        String[] ga = {"到达第一层", "到达第二层", "到达第三层", "到达第四层", "请求返回车库位于第几层", "请求返回前后侧红外状态"};
        garage_builder.setSingleChoiceItems(ga, -1, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i) {
                    case 0:  //到达第一层
                        FirstActivity.Connect_Transport.garage_control(0x0D,0x01, 0x01);
                        break;
                    case 1:  //到达第二层
                        FirstActivity.Connect_Transport.garage_control(0x0D,0x01, 0x02);
                        break;
                    case 2:  //到达第三层
                        FirstActivity.Connect_Transport.garage_control(0x0D,0x01, 0x03);
                        break;
                    case 3:  //到达第四层
                        FirstActivity.Connect_Transport.garage_control(0x0D,0x01, 0x04);
                        break;
                    case 4:  //请求返回车库位于第几层
                        FirstActivity.Connect_Transport.garage_control(0x0D,0x02, 0x01);
                        break;
                    case 5:  //请求返回前后侧红外状态
                        FirstActivity.Connect_Transport.garage_control(0x0D,0x02, 0x02);
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }

        });
        garage_builder.create().show();
    }

    private void gateController() {
        AlertDialog.Builder gt_builder = new AlertDialog.Builder(context);
        gt_builder.setTitle("闸门控制");
        String[] gt = {"开", "关", "显示车牌", "请求返回道闸状态"};
        gt_builder.setSingleChoiceItems(gt, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // 打开闸门
                                FirstActivity.Connect_Transport.gate(0x01, 0x01, 0x00, 0x00);
                                break;
                            case 1:
                                // 关闭闸门
                                FirstActivity.Connect_Transport.gate(0x01, 0x02, 0x00, 0x00);
                                break;
                            case 2:
                                //显示车牌
                                gate_plate_number();
                                break;
                            case 3:
                                //请求返回道闸状态
                                FirstActivity.Connect_Transport.gate(0x20, 0x01, 0x00, 0x00);
                                break;
                            default:
                                break;
                        }

                        dialog.dismiss();
                    }
                });
        gt_builder.create().show();
    }


    private void gate_plate_number() {
        AlertDialog.Builder gate_plate_builder = new AlertDialog.Builder(context);
        gate_plate_builder.setTitle("显示车牌");
        final String[] gate_Image_item = {"A123B4", "B567C8", "D910E1"};
        gate_plate_builder.setSingleChoiceItems(gate_Image_item, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO 自动生成的方法存根
                switch (which) {
                    case 0:
                        FirstActivity.Connect_Transport.gate(0x10, 'A', '1', '2');
                        FirstActivity.Connect_Transport.yanchi(500);
                        FirstActivity.Connect_Transport.gate(0x11, '3', 'B', '4');
                        break;
                    case 1:
                        FirstActivity.Connect_Transport.gate(0x10, 'B', '5', '6');
                        FirstActivity.Connect_Transport.yanchi(500);
                        FirstActivity.Connect_Transport.gate(0x11, '7', 'C', '8');
                        break;
                    case 2:
                        FirstActivity.Connect_Transport.gate(0x10, 'D', '9', '1');
                        FirstActivity.Connect_Transport.yanchi(500);
                        FirstActivity.Connect_Transport.gate(0x11, '0', 'E', '1');
                        break;
                }
                dialog.cancel();
            }
        });
        gate_plate_builder.create().show();
    }

    private void digital() {// 数码管
        AlertDialog.Builder dig_timeBuilder = new AlertDialog.Builder(
                context);
        dig_timeBuilder.setTitle("数码管");
        String[] dig_item = {"数码管显示", "数码管计时", "显示距离"};
        dig_timeBuilder.setSingleChoiceItems(dig_item, -1,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (which == 0) {// 数码管显示
                            digitalController();

                        } else if (which == 1) {// 数码管计时
                            digital_time();

                        } else if (which == 2) {// 显示距离
                            digital_dis();

                        }
                        dialog.dismiss();
                    }
                });
        dig_timeBuilder.create().show();
    }

    // 数码管显示方法
    private String[] itmes = {"1", "2"};
    int main, one, two, three;

    private void digitalController() {

        AlertDialog.Builder dg_Builder = new AlertDialog.Builder(
                context);
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_digital, null);
        dg_Builder.setTitle("数码管显示");
        dg_Builder.setView(view);
        // 下拉列表
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        final EditText editText1 = (EditText) view.findViewById(R.id.editText1);
        final EditText editText2 = (EditText) view.findViewById(R.id.editText2);
        final EditText editText3 = (EditText) view.findViewById(R.id.editText3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item, itmes);
        spinner.setAdapter(adapter);
        // 下拉列表选择监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                main = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        dg_Builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String ones = editText1.getText().toString();
                        String twos = editText2.getText().toString();
                        String threes = editText3.getText().toString();
                        // 显示数据，一个文本编译框最多两个数据显示数目管中两个数据
                        one = ones.equals("") ? 0x00 : Integer.parseInt(ones,16);
                        two = twos.equals("") ? 0x00 : Integer.parseInt(twos,16);
                        three = threes.equals("") ? 0x00 : Integer.parseInt(threes,16);
                        FirstActivity.Connect_Transport.digital(main, one, two, three);
                    }
                });

        dg_Builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });
        dg_Builder.create().show();
    }

    private int dgtime_index = -1;

    private void digital_time() {// 数码管计时
        AlertDialog.Builder dg_timeBuilder = new AlertDialog.Builder(
                context);
        dg_timeBuilder.setTitle("数码管计时");
        String[] dgtime_item = {"计时结束", "计时开始", "计时清零"};
        dg_timeBuilder.setSingleChoiceItems(dgtime_item, dgtime_index,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (which == 0) {// 计时结束
                            FirstActivity.Connect_Transport.digital_close();

                        } else if (which == 1) {// 计时开启
                            FirstActivity.Connect_Transport.digital_open();

                        } else if (which == 2) {// 计时清零
                            FirstActivity.Connect_Transport.digital_clear();

                        }
                        dialog.dismiss();
                    }
                });
        dg_timeBuilder.create().show();
    }

    private void digital_dis() {
        AlertDialog.Builder dis_timeBuilder = new AlertDialog.Builder(
                context);
        dis_timeBuilder.setTitle("显示距离");
        final String[] dis_item = {"100mm", "200mm", "400mm"};
        int dgdis_index = -1;
        dis_timeBuilder.setSingleChoiceItems(dis_item, dgdis_index,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {// 距离100mm
                            FirstActivity.Connect_Transport.digital_dic(Integer.parseInt(dis_item[which]
                                    .substring(0, 3)));
                        } else if (which == 1) {// 距离20mmm
                            FirstActivity.Connect_Transport.digital_dic(Integer.parseInt(dis_item[which]
                                    .substring(0, 3)));
                        } else if (which == 2) {// 距离400mm
                            FirstActivity.Connect_Transport.digital_dic(Integer.parseInt(dis_item[which]
                                    .substring(0, 3)));
                        }
                        dialog.dismiss();
                    }
                });
        dis_timeBuilder.create().show();
    }

    private TextView voiceText;

    private void voiceController() {
        AlertDialog.Builder dg_timeBuilder = new AlertDialog.Builder(
                context);
        dg_timeBuilder.setTitle("语音播报");
        String[] dgtime_item = {"语音播报随机指令", "语音播报指定内容"};
        dg_timeBuilder.setSingleChoiceItems(dgtime_item, dgtime_index,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (which == 0) {// 语音播报随机指令
                            FirstActivity.Connect_Transport.VoiceBroadcast();

                        } else if (which == 1) {// 语音播报指定内容
                            View view = LayoutInflater.from(context).inflate(
                                    R.layout.item_car, null);
                            voiceText = (EditText) view.findViewById(R.id.voiceText);

                            AlertDialog.Builder voiceBuilder = new AlertDialog.Builder(context);
                            voiceBuilder.setTitle("语音播报");
                            voiceBuilder.setView(view);
                            voiceBuilder.setPositiveButton("播报",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // TODO Auto-generated method stub
                                            String src = voiceText.getText().toString();
                                            if (src.equals("")) {
                                                src = "请输入你要播报的内容";
                                            }
                                            try {
                                                byte[] sbyte = bytesend(src.getBytes("GBK"));
                                                FirstActivity.Connect_Transport.send_voice(sbyte);
                                            } catch (UnsupportedEncodingException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                            dialog.cancel();
                                        }
                                    });
                            voiceBuilder.setNegativeButton("取消", null);
                            voiceBuilder.create().show();
                        }
                        dialog.dismiss();
                    }
                });
        dg_timeBuilder.create().show();
    }

    private byte[] bytesend(byte[] sbyte) {
        byte[] textbyte = new byte[sbyte.length + 5];
        textbyte[0] = (byte) 0xFD;
        textbyte[1] = (byte) (((sbyte.length + 2) >> 8) & 0xff);
        textbyte[2] = (byte) ((sbyte.length + 2) & 0xff);
        textbyte[3] = 0x01;// 合成语音命令
        textbyte[4] = (byte) 0x01;// 编码格式
        for (int i = 0; i < sbyte.length; i++) {
            textbyte[i + 5] = sbyte[i];
        }
        return textbyte;
    }

    private void magnetic_suspension() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("无线充电");
        String[] item2 = {"开"};
        builder.setSingleChoiceItems(item2, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (which == 0) {
                            FirstActivity.Connect_Transport.magnetic_suspension(0x01, 0x01, 0x00, 0x00);
                        }
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void TFT_LCD_A() {
        AlertDialog.Builder TFTbuilder = new AlertDialog.Builder(context);
        TFTbuilder.setTitle("TFT显示器A");
        String[] TFTitem = {"图片显示模式", "车牌显示", "计时模式", "距离显示", "HEX显示模式"};
        TFTbuilder.setSingleChoiceItems(TFTitem, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                TFT_Image_A();
                                break;
                            case 1:
                                TFT_plate_number_A();
                                break;
                            case 2:
                                TFT_Timer_A();
                                break;
                            case 3:
                                Distance_A();
                                break;
                            case 4:
                                Hex_show_A();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
        TFTbuilder.create().show();
    }

    private void TFT_Image_A() {
        AlertDialog.Builder TFT_Image_builder = new AlertDialog.Builder(context);
        TFT_Image_builder.setTitle("图片显示模式");
        String[] TFT_Image_item = {"指定显示", "上翻一页", "下翻一页", "自动翻页"};
        TFT_Image_builder.setSingleChoiceItems(TFT_Image_item, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO 自动生成的方法存根
                switch (which) {
                    case 0:
                        TFT_A_show();
                        break;
                    case 1:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x10, 0x01, 0x00, 0x00);
                        break;
                    case 2:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x10, 0x02, 0x00, 0x00);
                        break;
                    case 3:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x10, 0x03, 0x00, 0x00);
                        break;
                }
                dialog.cancel();
            }
        });
        TFT_Image_builder.create().show();
    }

    private void TFT_B_show() {
        AlertDialog.Builder TFT_Image_builder = new AlertDialog.Builder(context);
        TFT_Image_builder.setTitle("指定图片显示");
        String[] TFT_Image_item = {"1", "2", "3", "4", "5"};
        TFT_Image_builder.setSingleChoiceItems(TFT_Image_item, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO 自动生成的方法存根
                switch (which) {
                    case 0:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x10, 0x01, 0x00, 0x00);
                        break;
                    case 1:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x10, 0x02, 0x00, 0x00);
                        break;
                    case 2:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x10, 0x03, 0x00, 0x00);
                        break;
                    case 3:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x10, 0x04, 0x00, 0x00);
                        break;
                    case 4:
                        FirstActivity.Connect_Transport.TFT_LCD(0x08,0x10, 0x05, 0x00, 0x00);
                        break;
                }
                dialog.cancel();
            }
        });
        TFT_Image_builder.create().show();
    }

    private void TFT_A_show() {
        AlertDialog.Builder TFT_Image_builder = new AlertDialog.Builder(context);
        TFT_Image_builder.setTitle("指定图片显示");
        String[] TFT_Image_item = {"1", "2", "3", "4", "5"};
        TFT_Image_builder.setSingleChoiceItems(TFT_Image_item, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO 自动生成的方法存根
                switch (which) {
                    case 0:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x10, 0x01, 0x00, 0x00);
                        break;
                    case 1:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x10, 0x02, 0x00, 0x00);
                        break;
                    case 2:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x10, 0x03, 0x00, 0x00);
                        break;
                    case 3:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x10, 0x04, 0x00, 0x00);
                        break;
                    case 4:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x10, 0x05, 0x00, 0x00);
                        break;
                }
                dialog.cancel();
            }
        });
        TFT_Image_builder.create().show();
    }


    private void TFT_plate_number_A() {
        AlertDialog.Builder TFT_plate_builder = new AlertDialog.Builder(context);
        TFT_plate_builder.setTitle("车牌显示模式");
        final String[] TFT_Image_item = {"A123B4", "B567C8", "D910E1"};
        TFT_plate_builder.setSingleChoiceItems(TFT_Image_item, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO 自动生成的方法存根
                switch (which) {
                    case 0:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x20, 'A', '1', '2');
                        FirstActivity.Connect_Transport.yanchi(500);
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x21, '3', 'B', '4');
                        break;
                    case 1:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x20, 'B', '5', '6');
                        FirstActivity.Connect_Transport.yanchi(500);
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x21, '7', 'C', '8');
                        break;
                    case 2:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x20, 'D', '9', '1');
                        FirstActivity.Connect_Transport.yanchi(500);
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x21, '0', 'E', '1');
                        break;
                }
                dialog.cancel();
            }
        });
        TFT_plate_builder.create().show();
    }

    private void TFT_Timer_A() {
        AlertDialog.Builder TFT_Iimer_builder = new AlertDialog.Builder(context);
        TFT_Iimer_builder.setTitle("计时模式");
        String[] TFT_Image_item = {"开始", "关闭", "停止"};
        TFT_Iimer_builder.setSingleChoiceItems(TFT_Image_item, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO 自动生成的方法存根
                switch (which) {
                    case 0:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x30, 0x01, 0x00, 0x00);
                        break;
                    case 1:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x30, 0x02, 0x00, 0x00);
                        break;
                    case 2:
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x30, 0x00, 0x00, 0x00);
                        break;
                }
                dialog.cancel();
            }
        });
        TFT_Iimer_builder.create().show();
    }

    private void Distance_A() {
        AlertDialog.Builder TFT_Distance_builder = new AlertDialog.Builder(context);
        TFT_Distance_builder.setTitle("距离显示模式");
        String[] TFT_Image_item = {"100mm", "200mm", "300mm"};
        TFT_Distance_builder.setSingleChoiceItems(TFT_Image_item, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO 自动生成的方法存根
                if (which == 0) {
                    FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x50, 0x00, 0x01, 0x00);
                }
                if (which == 1) {
                    FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x50, 0x00, 0x02, 0x00);
                }
                if (which == 2) {
                    FirstActivity.Connect_Transport.TFT_LCD(0x0B,0x50, 0x00, 0x03, 0x00);
                }
                dialog.cancel();
            }
        });
        TFT_Distance_builder.create().show();
    }

    private void Hex_show_A() {

        AlertDialog.Builder TFT_Hex_builder = new AlertDialog.Builder(
                context);
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_hex, null);
        TFT_Hex_builder.setTitle("HEX显示模式");
        TFT_Hex_builder.setView(view);
        // 下拉列表
        final EditText editText1 = (EditText) view.findViewById(R.id.editText1);
        final EditText editText2 = (EditText) view.findViewById(R.id.editText2);
        final EditText editText3 = (EditText) view.findViewById(R.id.editText3);
        TFT_Hex_builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String ones = editText1.getText().toString();
                        String twos = editText2.getText().toString();
                        String threes = editText3.getText().toString();
                        // 显示数据，一个文本编译框最多两个数据显示数目管中两个数据
                        one = ones.equals("") ? 0x00 : Integer.parseInt(ones,16);
                        two = twos.equals("") ? 0x00 : Integer.parseInt(twos,16);
                        three = threes.equals("") ? 0x00 : Integer.parseInt(threes,16);
                        FirstActivity.Connect_Transport.TFT_LCD(0x0B, 0x40, one, two, three);
                    }
                });
        TFT_Hex_builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });
        TFT_Hex_builder.create().show();
    }

}