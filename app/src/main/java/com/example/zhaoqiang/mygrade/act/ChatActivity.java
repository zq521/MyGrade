package com.example.zhaoqiang.mygrade.act;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.ada.ChatAdapter;
import com.example.zhaoqiang.mygrade.callback.MessageListListener;
import com.example.zhaoqiang.mygrade.fragment.EmojiFragment;
import com.example.zhaoqiang.mygrade.fragment.ImageFragment;
import com.example.zhaoqiang.mygrade.magnager.MessageManager;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVideoMessageBody;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnDownloadFileChangeListener;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.wlf.filedownloader.FileDownloader.registerDownloadStatusListener;

/**
 * Created by 轩韩子 on 2017/3/29.
 * at 19:37
 * 聊天详情页面
 */

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, EMMessageListener, MessageListListener, OnFileDownloadStatusListener, OnDownloadFileChangeListener {
    String newPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "FileDownloader";
    private static final int OPEN_VIDEO = 102;
    private String text;
    private String userName;
    private Button chat_btn_send;
    private ListView chat_listView;
    private ChatAdapter chatAdapter;
    private EditText chat_et_content;
    private ImageButton chat_btn_jiahao;
    private EMConversation emConversation;
    private ImageFragment imageFragment;
    private EmojiFragment emjoeFragment;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private ArrayList<EMMessage> sendMessagelist = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            chatAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chat);
        //接收消息监听
        EMClient.getInstance().chatManager().addMessageListener(this);
        //下载监听
        registerDownloadStatusListener(this);
        //初始化控件
        init();
        InitFragment();
        //设置系统自带标题栏内容
        setBar();
    }

    /*
     * 初始化控件
     */
    private void init() {
        //接收跳转过来的数据
        userName = getIntent().getStringExtra("userName");
        text = getIntent().getStringExtra("text");
        //初始化控件
        chat_btn_send = (Button) findViewById(R.id.chat_btn_send);
        chat_listView = (ListView) findViewById(R.id.chat_listView);
        chat_et_content = (EditText) findViewById(R.id.chat_et_content);
        chat_btn_jiahao = (ImageButton) findViewById(R.id.chat_btn_jiahao);
        chat_btn_send.setOnClickListener(this);
        chat_btn_jiahao.setOnClickListener(this);

        //如果text不能空，设置数据到输入框
        if (!TextUtils.isEmpty(text)) {
            chat_et_content.setText(text);
            chat_et_content.setSelection(chat_et_content.getText().length());
        }
        //为输入框的文字添加监听事件
        chat_et_content.addTextChangedListener(new TextWatcher() {
            //旧的文本长度处理监听
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //调用此方法,通知刚刚取代旧的文本长度
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //通知你在某个地方文本已经改变
            @Override
            public void afterTextChanged(Editable s) {
                text = s.toString();
            }
        });

        //获取与某个人的所有会话
        emConversation = EMClient.getInstance().chatManager().getConversation(userName);
        //清空所有会话
        sendMessagelist.clear();
        //设置数据源
        sendMessagelist = (ArrayList<EMMessage>) emConversation.getAllMessages();
        chatAdapter = new ChatAdapter(this, sendMessagelist);
        handler.sendMessage(new Message());
        chat_listView.setAdapter(chatAdapter);
    }

    /*
     点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_btn_send:
                //发送消息
                sendText();
                chat_et_content.setText("");
                text = "";
                chatAdapter.notifyDataSetChanged();
                break;
            case R.id.chat_btn_jiahao:
                //弹出菜单
                showPopupMenu(chat_btn_jiahao);
        }
    }

    /*
     发送文本消息
     */
    public void sendText() {
        String send = chat_et_content.getText().toString();
        if (send.equals("")) {
            Toast.makeText(this, "不能发送空白信息", Toast.LENGTH_SHORT).show();
        } else {
            //创建一条文本消息
            EMMessage message = EMMessage.createTxtSendMessage(send, userName);
            sendMessage(message);
        }
    }

    /*
     * 发送图片
     * @param imgPath  本地路径
     * @param isThumbnail  是否发送原图
     */
    public void sendImage(String imgPath, boolean isThumbnail) {
        //imagePath为图片本地路径，false为不发送原图，
        EMMessage message = EMMessage.createImageSendMessage(imgPath, false, userName);
        sendMessage(message);
        imageFrament();

    }

    /*
    发送
     */
    private void sendMessage(EMMessage message) {
        //设置文本类型
        message.setChatType(EMMessage.ChatType.Chat);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                //滚动
                chat_listView.setSelection(sendMessagelist.size());
                Log.e("message", "onSuccess");
            }

            @Override
            public void onError(int i, String s) {

                Log.e("message", "onError=" + i + "   " + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        //将消息添加到 数据源
        sendMessagelist.add(message);
        //刷新消息列表
        chatAdapter.notifyDataSetChanged();
        //调用刷新消息列表的方法
        MessageManager.getInsatance().setMessageListener(this);
    }

    /*
     弹出菜单
     */
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        //设置监听
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_image:
                        //加载图片fragment
                        imageFrament();
                        break;
                    case R.id.menu_video:
                        //调用视频录像
                        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        //限制时长
                        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                        //开启摄像机
                        startActivityForResult(intent, OPEN_VIDEO);
                        break;
                    case R.id.menu_speak:
                        break;

                    case R.id.menu_emjeo:
                        //判断是否打开
                        if (emjoeFragment.isAdded()) {
                            transaction = fragmentManager.beginTransaction();
                            transaction.remove(emjoeFragment);
                            transaction.commit();
                            fragmentManager.popBackStackImmediate("emjoeFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        } else {
                            transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.message_bottom_fragment_lay, emjoeFragment);
                            transaction.addToBackStack("emjoeFragment");
                            transaction.commit();
                        }

                        break;
                }
                Toast.makeText(ChatActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }

        });
        //显示
        popupMenu.show();
    }

    /*
    回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case OPEN_VIDEO:
                if (resultCode == RESULT_OK) {
                    //获取视频路径
                    String path = getpath(data.getData());
                    //实例化
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    //设置数据源
                    try {
                        mediaPlayer.setDataSource(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //获取时长
                    int duration = mediaPlayer.getDuration();
                    //实例化
                    MediaMetadataRetriever mediaRecorder = new MediaMetadataRetriever();
                    //设置数据源
                    mediaRecorder.setDataSource(path);
                    //获取某一帧 图像，并写入文件
                    File file1 = creatImage(mediaRecorder.getFrameAtTime(1000));
                    //释放资源
                    mediaPlayer.release();
                    mediaRecorder.release();
                    EMMessage videoMsg = EMMessage.createVideoSendMessage(
                            path                        //视频路径
                            , file1.getAbsolutePath()    //视频预览图片路径
                            , duration //"视频长度"
                            , userName);//用户名
                    //发送视频
                    sendMessage(videoMsg);
                }
                break;
        }

    }


    private File creatImage(Bitmap bitmap) {
        //获得根目录
        File file = new File(Environment.getExternalStorageDirectory()
                , System.currentTimeMillis() + ".jpg");
        try {
            //开启文件的输出流
            FileOutputStream out = new FileOutputStream(file);
            //把内容写入输出流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            System.out.println("保存到");
            //刷新输出流
            out.flush();
            //关闭输出流
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    /*
    根据视频文件uri获取path
     */
    public String getpath(Uri uri) {
        //定义 需要查询的字段
        String[] projirction = {MediaStore.Video.Media.DATA};
        //查询该uri
        Cursor cursor = managedQuery(uri, projirction, null, null, null);
        //获取 所需字段 对应的列下表
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        //将游标 指针移动到第一个
        cursor.moveToNext();
        //返回 根据字段下标 获取取出来的数据
        return cursor.getString(column_index);
    }

    /*
     加载图片fragment
    */
    private void imageFrament() {
        //判断是否打开
        if (imageFragment.isAdded()) {
            transaction = fragmentManager.beginTransaction();
            transaction.remove(imageFragment);
            transaction.commit();
            fragmentManager.popBackStackImmediate("myFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.message_bottom_fragment_lay, imageFragment);
            transaction.addToBackStack("myFragment");
            transaction.commit();
        }
    }

    /*
     销毁会话
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除会话
        EMClient.getInstance().chatManager().removeMessageListener(this);
        //取消下载监听
        FileDownloader.unregisterDownloadFileChangeListener(this);
    }

    /*
    消息收到
     * @param list
     */
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        //收到消息
        this.sendMessagelist.addAll(list);
        handler.sendMessage(new Message());
        //本地缩略图路径的视频消息集合
        ArrayList<EMMessage> list1 = new ArrayList<>();
        //遍历接收到的消息
        for (EMMessage msg : list
                ) {
            //处理视频消息
            switch (msg.getType()) {
                case VIDEO:
//                    获取消息体
                    EMVideoMessageBody body2 = (EMVideoMessageBody) msg.getBody();
                    //文件名
                    String name = System.currentTimeMillis() + ".jpg";
                    //下载视频缩略图
                    FileDownloader.createAndStart(body2.getThumbnailUrl(), newPath, name);
                    //把本地路径设置给消息体
                    body2.setLocalThumb(newPath + "/" + name);
                    //把消息体添加放到 消息对象中
                    msg.addBody(body2);
                    //把修改过来数据对象添加到集合中
                    list1.add(msg);
                    this.sendMessagelist.add(msg);
                    break;
                default:
                    this.sendMessagelist.add(msg);
                    break;

            }
            //把消息添加到数据库中
            EMClient.getInstance().chatManager().importMessages(list1);
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
        //收到透传消息
    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {
        //收到已送达回执
    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {
        //收到已送达回执
    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {
        //消息状态变动
    }

    /*
    刷新列表
     */
    @Override
    public void refChatList() {
        chatAdapter.notifyDataSetChanged();
    }


    /*
   设置标题栏
    */
    private void setBar() {
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    设置标题栏点击事件监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    加载fragment
     */

    private void InitFragment() {
        fragmentManager = getSupportFragmentManager();
        imageFragment = new ImageFragment();
        emjoeFragment = new EmojiFragment();


    }

    /*
    返回键传递数据，返回结果
    */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("text", text);
        intent.putExtra("username", userName);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void onFileDownloadStatusWaiting(DownloadFileInfo downloadFileInfo) {
        // 等待下载
    }

    @Override
    public void onFileDownloadStatusPreparing(DownloadFileInfo downloadFileInfo) {
        // 准备中
    }

    @Override
    public void onFileDownloadStatusPrepared(DownloadFileInfo downloadFileInfo) {
        // 已准备好
    }

    @Override
    public void onFileDownloadStatusDownloading(DownloadFileInfo downloadFileInfo, float downloadSpeed, long remainingTime) {
        // 正在下载
    }

    @Override
    public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {
        // 下载已被暂停 
    }

    @Override
    public void onFileDownloadStatusCompleted(DownloadFileInfo downloadFileInfo) {
        Toast.makeText(this, "下载成功", Toast.LENGTH_SHORT).show();
        // 下载完成
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFileDownloadStatusFailed(String url, DownloadFileInfo downloadFileInfo, FileDownloadStatusFailReason failReason) {
        // 下载失败了
    }

    @Override
    public void onDownloadFileCreated(DownloadFileInfo downloadFileInfo) {
// 一个新下载文件被创建，也许你需要同步你自己的数据存储，比如在你的业务数据库中增加一条记录 
    }

    @Override
    public void onDownloadFileUpdated(DownloadFileInfo downloadFileInfo, Type type) {
        // 一个下载文件被更新，也许你需要同步你自己的数据存储，比如在你的业务数据库中更新一条记录 
    }

    @Override
    public void onDownloadFileDeleted(DownloadFileInfo downloadFileInfo) {
        // 一个下载文件被删除，也许你需要同步你自己的数据存储，比如在你的业务数据库中删除一条记录 
    }
}


