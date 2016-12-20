**今天是学习Android开发的第十四天**
_________________________________________________

今天我们开始学习Android下的数据持久化其中一个重要部分──数据库

https://github.com/73Group/HomeWorkAll/tree/master/Day_14

### 1.Android内置数据库介绍
Android系统中内置一个轻量级的SQL数据库，该数据库占用资源极低，处理速度也非常快，叫做SQLite。
SQLite数据库广泛应用于嵌入式设备，手持设备等，Android/IOS/Windows Phone等主流手机操作系统都内置了SQLite数据库。　　　

SQLite数据库与MySQL，Oracle和SQLServer有着明显的不同，后三者都是采用Socket连接的方式为应用程序提供服务，
而SQLite是嵌入到应用程序之中，基于本地文件的方式为应用程序提供服务，类似于微软的Access数据库，可以将其理解为一个高效的数据引擎。

Android系统中内置的SQLite版本是SQLite3。


### 2.SQL语句
***数据类型***           
SQLite3所使用的SQL语句语法与我们所学的SQL基本一致，需要注意一下，SQLite3所支持的数据类型只有NULL， INTEGER， REAL， TEXT， BLOB这五种。
其他SQL数据库系统中的常用类型也可以使用，它们会被自动转换成所支持的最合适的数据类型。
比如在创建一个表时，可以在 CREATE TABLE 语句中指定某列的数据类型，但是你可以把任何数据类型放入任何列中。当某个值插入数据库时，SQLite 将检查它的类型。如果该类型与关联的列不匹配，则 SQLite 会尝试将该值转换成该列的类型。如果不能转换，则该值将作为其本身具有的类型存储。比如可以把一个字符串（String）放入 INTEGER 列。

具体规则你可以看这里：           
http://www.runoob.com/sqlite/sqlite-data-types.html

***常用函数***               
http://www.runoob.com/sqlite/sqlite-functions.html

***内置函数大全***                
http://us.nodate.date/2015/06/24/sqlite%E5%87%BD%E6%95%B0%E5%A4%A7%E5%85%A8/          

注：SQLite支持视图，索引和触发器            


### 3.Android数据库使用步骤
***SDK中的SQLite3工具***              
在Android SDK目录下的platform-tools目录中，有一个sqlite3的可执行文件，这就是sqlite3的命令行工具。使用该工具可以进行SQLite数据库的创建，修改与查询。
一般此工具是用来调试用的，比如可以先在电脑上尝试使用SQLite3工具执行SQL语句，确保SQL语句正确后再其写带代码中。
也可以把Android设备中某个应用程序的SQLite数据库文件导出，使用SQLite3工具进行查询操作以定位问题所在。

***在应用程序中使用SQLite数据库***               
在应用程序中，使用Context的方法openOrCreateDatabase可以创建或打开一个SQLite数据库，
它会自动去检测是否存在这个数据库，如果存在则打开，如果不存在则创建一个数据库：
创建成功则返回一个SQLiteDatebase对象，否则抛出异常FileNotFoundException。
利用SQLiteDatebase对象就可以对数据库进行各项操作。但是我们一般情况下很少直接使用openOrCreateDatabase去
打开数据库，因为这种方式没有版本的控制，今后可能的数据库模型升级会有些麻烦。

Android 提供了 SQLiteOpenHelper 帮助你创建一个数据库，你只要继承 SQLiteOpenHelper 类，就可以轻松的创建数据库。
SQLiteOpenHelper 类根据开发应用程序的需要，封装了创建和更新数据库使用的逻辑。SQLiteOpenHelper 的子类，至少需要实现三个方法：
1. 构造函数，调用父类 SQLiteOpenHelper 的构造函数。这个方法需要四个参数：上下文环境（Context），数据库名字，一个可选的游标工厂（通常是null），一个代表你正在使用的数据库模型版本的整数。
2. onCreate() 方法，它在数据库第一次被创建的时候调用，用于初始化数据库（创建数据库，表，填入初始数据等）
3. onUpgrage() 方法，它在数据库模型版本需要升级时调用，它有三个参数，一个 SQLiteDatabase 对象，一个旧的版本号和一个新的版本号，这样你就可以清楚如何把一个数据库从旧的模型转变到新的模型。

当你完成了对数据库的操作，需要调用 SQLiteDatabase 的 Close() 方法来释放掉数据库连接。


###  作业          
练习使用Android的SQLite数据库：                 
编写一个DEMO程序，记录用户使用该软件的时间（包括进入时间，退出时间，在前台的时间，在后台的时间），
用户每次打开软件时，在界面上显示之前所有的统计记录(使用RecyclerView)，其中每条的显示格式为:                              
xx月xx日 xx:xx:xx 使用了x分钟，其中前台时间x分钟。            
比如：     
12月20日 17:20:11 使用了8分钟，其中前台时间2分钟。         
或者：          
12月20日 17:30:11 使用了8分钟，其中前台时间34秒。      


### 建议
原则上新内容的学习在白天完成，作业在晚上完成（19:00之后）。
但如果白天学习了新内容之后，觉得时间比较充裕，也可以自行决定是否开始去完成作业内容。                     
如果白天你独立完成了作业内容，则之后的作业难度会逐步增加。                 
如果到了晚上任然没有按时完成作业内容，则第二天推送内容延缓，等到完成作业之后继续推送，并降低作业难度。             





### The end
我的灵在我里面发昏的时候，你知道我的道路。在我行的路上，敌人为我暗设网罗。
求你向我右边观看，因为没有人认识我。我无处避难，也没有人眷顾我。
