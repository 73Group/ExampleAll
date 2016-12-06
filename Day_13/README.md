**今天是学习Android开发的第十三天**
_________________________________________________

由于你昨天和前天的任务还没有完成，所以今天的任务比较轻。        
今天我们开始学习Android下的数据持久化（文件存取，键值对存取，数据库等）的相关问题。

https://github.com/73Group/HomeWorkAll/tree/master/Day_13

### 1.文件操作
Android中的文件操作与Java中的文件操作与没有什么本质上的不同。Android中使用的也都是Java中的文件操作类。下面我先来主要介绍一下在Android中进行文件操作的要点和注意事项：
（1） Android分为内部存储（存储用户程序和程序私有数据）和外部存储（媒体文件，文档和各类其他文件）。
（2） 一个包名为com.hfutcx.example的应用程序，它的私有内部存储路径是/data/data/com.hfutcx.example/files，在这个路径下，该应用程序可以自由读写。
（3） 应用程序如果要在外部存储进行文件读写操作，必须要请求文件读写的权限。在Android 6.0（API 23）以下的版本中，只需要在AndroidManifest.xml文件在添加（在manifest标签内，application标签外）如下声明即可：

```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```
或者

```
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

 而在Android 6.0以上的系统中，则需要动态申请权限，这个比较麻烦，这里暂时不做讨论。你可以把应用程序的targetSdkVersion设置为22（就像我的示例中的app/build.gradle中配置的那样），这样就不需要考虑Android 6.0以上版本的动态申请权限的问题了。这样即使应用程序在Android 6.0以上的系统中运行，系统也会默认为其授予权限。
（4） Android中常用的文件路径获取方式：

```
 Environment.getDataDirectory();  // 获取/data目录的路径
 Environment.getExternalStorageDirectory(); // 获取SD卡（外置存储）的根目录路径
 getFilesDir(); // 获取应用程序私有存储路径，Context的方法
 getExternalCacheDir(); //获取外置存储卡中的供本应用使用的缓存目录，Context的方法
 getExternalFileDir(null); //获取外置存储卡中的供本应用使用的私有目录，Context的方法
```
（5） 具体的文件IO操作完全跟Java的文件IO操作一致，如果你忘了，你可以复习一下。
文件操作的Android官方教程：
https://developer.android.com/training/basics/data-storage/files.html



### 2.键值对存取
 SharedPreferences 是Android提供的一种方便快捷地保存和读取键值对的接口。它本质上是在应用程序私有目录下生成对应的xml文件保存键值对操作，但Android系统优化的其IO性能，并且我们不需要自己去解析xml文件。

>  SharedPreferences 类提供了一个通用框架，以便您能够保存和检索原始数据类型的永久性键值对。 您可以使用
> SharedPreferences 来保存任何原始数据：布尔值、浮点值、整型值、长整型和字符串。
> 要获取应用的 SharedPreferences 对象，请使用以下两个方法之一：
> getSharedPreferences() - 如果您需要多个按名称（使用第一个参数指定）识别的首选项文件，请使用此方法。
> getPreferences() - 如果您只需要一个用于 Activity 的首选项文件，请使用此方法。 由于这将是用于 Activity
> 的唯一首选项文件，因此无需提供名称。
> 要写入值：
> 调用 edit() 以获取 SharedPreferences.Editor。 使用 putBoolean() 和 putString()
> 等方法添加值。 使用 commit() 提交新值 要读取值，请使用 getBoolean() 和 getString() 等
> SharedPreferences 方法。

以下是在计算器中保存静音按键模式首选项的示例：

```
public class Calc extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle state){
       super.onCreate(state);
       . . .

       // Restore preferences
       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
       boolean silent = settings.getBoolean("silentMode", false);
       setSilent(silent);
    }

    @Override
    protected void onStop(){
       super.onStop();

      // We need an Editor object to make preference changes.
      // All objects are from android.context.Context
      SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
      SharedPreferences.Editor editor = settings.edit();
      editor.putBoolean("silentMode", mSilentMode);

      // Commit the edits!
      editor.commit();
    }
}
```


 关于的Android官方使用教程：
 https://developer.android.com/training/basics/data-storage/shared-preferences.html


###  作业
 练习使用Android的文件操作和SharedPreferences

### The end
失败只有一种，那就是半途而废。
