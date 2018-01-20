package com.xust.wtc.Controller.book;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.book.BookService;
import com.xust.wtc.utils.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Spirit on 2017/12/5.
 */
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/addBook", method = RequestMethod.POST,
                consumes = "application/json", produces = "application/json")
    public Result addBook(HttpSession httpSession, @RequestBody String isbn) {
        JsonNode jsonNode = StringConverter.converterToJsonNode(isbn);
        String sessionId = httpSession.getId();
        return bookService.addBook(StringConverter.converterToString(jsonNode, "isbn"), sessionId);
    }

    public static void main(String[] args) {
        String isbn = "{  \"author\":[\"David Flanagan\"]," +
                            "\"pubdate\":\"2012-4-1\"," +
                "\"image\":\"https://img3.doubanio.com\\/mpic\\/s8958854.jpg\"," +
                "\"translator\":[\"淘宝前端团队\"], " +
                "\"pages\":\"1004\"," +
                "\"publisher\":\"机械工业出版社华章公司\"," +
                "\"title\":\"JavaScript权威指南(第6版)\"," +
                "\"summary\":\"本书是程序员学习核心JavaScript语言和由Web浏览器定义的JavaScript API的指南和综合参考手册。第6版涵盖HTML 5和ECMAScript 5。很多章节完全重写，以便与时俱进，紧跟当今的最佳Web开发实践。本书新增章节描述了jQuery和服务器端JavaScript。本书适合那些希望学习Web编程语言的初、中级程序员和希望精通JavaScript的JavaScript程序员阅读。\"," +
                "\"price\":\"139.00元\"}";

        String s = "{\"rating\":{\"max\":10,\"numRaters\":495,\"average\":\"8.9\",\"min\":0},\"subtitle\":\"\",\"author\":[\"David Flanagan\"],\"pubdate\":\"2012-4-1\",\"tags\":[{\"count\":785,\"name\":\"JavaScript\",\"title\":\"JavaScript\"},{\"count\":266,\"name\":\"Web前端开发\",\"title\":\"Web前端开发\"},{\"count\":239,\"name\":\"犀牛书\",\"title\":\"犀牛书\"},{\"count\":170,\"name\":\"前端\",\"title\":\"前端\"},{\"count\":116,\"name\":\"编程\",\"title\":\"编程\"},{\"count\":111,\"name\":\"前端开发\",\"title\":\"前端开发\"},{\"count\":111,\"name\":\"Web开发\",\"title\":\"Web开发\"},{\"count\":107,\"name\":\"计算机\",\"title\":\"计算机\"}],\"origin_title\":\"JavaScript: The Definitive Guide, Sixth Edition\",\"image\":\"https://img3.doubanio.com\\/mpic\\/s8958854.jpg\",\"binding\":\"平装\",\"translator\":[\"淘宝前端团队\"],\"catalog\":\"前言  1\\n第1章 JavaScript概述  5\\n1.1 JavaScript语言核心  8\\n1.2 客户端JavaScript  12\\n第一部分 JavaScript 语言核心\\n第2章 词法结构  25\\n2.1 字符集  25\\n2.2 注释  27\\n2.3 直接量  27\\n2.4 标识符和保留字  28\\n2.5 可选的分号  30\\n第3章 类型、值和变量  32\\n3.1 数字  34\\n3.2 文本  38\\n3.3 布尔值  43\\n3.4 null和undefined  44\\n3.5 全局对象  45\\n3.6 包装对象  46\\n3.7 不可变的原始值和可变的对象引用  47\\n3.8 类型转换  48\\n3.9 变量声明  55\\n3.10 变量作用域  56\\n第4章 表达式和运算符  60\\n4.1 原始表达式  60\\n4.2 对象和数组的初始化表达式  61\\n4.3 函数定义表达式  62\\n4.4 属性访问表达式  63\\n4.5 调用表达式  64\\n4.6 对象创建表达式  64\\n4.7 运算符概述  65\\n4.8 算术表达式  69\\n4.9 关系表达式  74\\n4.10 逻辑表达式  79\\n4.11 赋值表达式  81\\n4.12 表达式计算  83\\n4.13 其他运算符  86\\n第5章 语句  91\\n5.1 表达式语句  92\\n5.2 复合语句和空语句  92\\n5.3 声明语句  94\\n5.4 条件语句  96\\n5.5 循环  101\\n5.6 跳转  106\\n5.7 其他语句类型  113\\n5.8 JavaScript语句小结  116\\n第6章 对象  118\\n6.1 创建对象  120\\n6.2 属性的查询和设置  123\\n6.3 删除属性  127\\n6.4 检测属性  128\\n6.5 枚举属性  130\\n6.6 属性getter和setter  132\\n6.7 属性的特性  134\\n6.8 对象的三个属性  138\\n6.9 序列化对象  141\\n6.10 对象方法  142\\n第7章 数组  144\\n7.1 创建数组  144\\n7.2 数组元素的读和写  145\\n7.3 稀疏数组  147\\n7.4 数组长度  148\\n7.5 数组元素的添加和删除  149\\n7.6 数组遍历  149\\n7.7 多维数组  151\\n7.8 数组方法  152\\n7.9 ECMAScript 5中的数组方法  156\\n7.10 数组类型  160\\n7.11 类数组对象  161\\n7.12 作为数组的字符串  163\\n第8章 函数  165\\n8.1 函数定义  166\\n8.2 函数调用  168\\n8.3 函数的实参和形参  173\\n8.4 作为值的函数  178\\n8.5 作为命名空间的函数  181\\n8.6 闭包  182\\n8.7 函数属性、方法和构造函数  188\\n8.8 函数式编程  194\\n第9章 类和模块  201\\n9.1 类和原型  202\\n9.2 类和构造函数  203\\n9.3 JavaScript中Java式的类继承  207\\n9.4 类的扩充  210\\n9.5 类和类型  212\\n9.6 JavaScript中的面向对象技术  217\\n9.7 子类  230\\n9.8 ECMAScript 5 中的类  239\\n9.9 模块  248\\n第10章 正则表达式的模式匹配  253\\n10.1 正则表达式的定义  253\\n10.2 用于模式匹配的String方法  261\\n10.3 RegExp对象  263\\n第11章 JavaScript的子集和扩展  267\\n11.1 JavaScript的子集  268\\n11.2 常量和局部变量  271\\n11.3 解构赋值  274\\n11.4 迭代  276\\n11.5 函数简写  285\\n11.6 多Catch 从句  285\\n11.7 E4X: ECMAScript for XML  286\\n第12章 服务器端JavaScript  290\\n12.1 用Rhino脚本化Java  291\\n12.2 用Node实现异步I\\/O  297\\n第二部分 客户端JavaScript\\n第13章 Web浏览器中的JavaScript  309\\n13.1 客户端JavaScript  309\\n13.2 在HTML里嵌入JavaScript  313\\n13.3 JavaScript程序的执行  319\\n13.4 兼容性和互用性  326\\n13.5 可访问性  333\\n13.6 安全性  334\\n13.7 客户端框架  339\\n第14章 Window对象  341\\n14.1 计时器  342\\n14.2 浏览器定位和导航  343\\n14.3 浏览历史  345\\n14.4 浏览器和屏幕信息  346\\n14.5 对话框  348\\n14.6 错误处理  351\\n14.7 作为Window对象属性的文档元素  351\\n14.8 多窗口和窗体  353\\n第15章 脚本化文档  361\\n15.1 DOM概览  362\\n15.2 选取文档元素  364\\n15.3 文档结构和遍历  371\\n15.4 属性  375\\n15.5 元素的内容  378\\n15.6 创建、插入和删除节点  382\\n15.7 例子：生成目录表  387\\n15.8 文档和元素的几何形状和滚动  389\\n15.9 HTML表单  396\\n15.10 其他文档特性  404\\n第16章 脚本化CSS  410\\n16.1 CSS概览  411\\n16.2 重要的CSS属性  416\\n16.3 脚本化内联样式  427\\n16.4 查询计算出的样式  431\\n16.5 脚本化CSS类  433\\n16.6 脚本化样式表  435\\n第17章 事件处理  440\\n17.1 事件类型  442\\n17.2 注册事件处理程序  451\\n17.3 事件处理程序的调用  454\\n17.4 文档加载事件  459\\n17.5 鼠标事件  461\\n17.6 鼠标滚轮事件  465\\n17.7 拖放事件  468\\n17.8 文本事件  475\\n17.9 键盘事件  478\\n第18章 脚本化HTTP  484\\n18.1 使用XMLHttpRequest  487\\n18.2 借助<script>发送HTTP请求：JSONP  505\\n18.3 基于服务器端推送事件的Comet技术  508\\n第19章 jQuery类库  514\\n19.1 jQuery基础  515\\n19.2 jQuery的getter和setter  522\\n19.3 修改文档结构  528\\n19.4 用jQuery处理事件  531\\n19.5 动画效果  542\\n19.6 jQuery中的Ajax  550\\n19.7 工具函数  563\\n19.8 jQuery选择器和选取方法  566\\n19.9 jQuery的插件扩展  574\\n19.10 jQuery UI类库  577\\n第20章 客户端存储  579\\n20.1 localStorage和sessionStorage  581\\n20.2 cookie  586\\n20.3 利用IE userData来持久化数据  592\\n20.4 应用程序存储和离线Web应用  594\\n第21章 多媒体和图形编程  606\\n21.1 脚本化图片  606\\n21.2 脚本化音频和视频  608\\n21.3 SVG：可伸缩的矢量图形  615\\n21.4 <canvas>中的图形  623\\n第22章 HTML5 API  658\\n22.1 地理位置  659\\n22.2 历史记录管理  662\\n22.3 跨域消息传递  668\\n22.4 Web Workers  671\\n22.5 类型化数组和ArrayBuffer  678\\n22.6 Blob  682\\n22.7 文件系统API  691\\n22.8 客户端数据库  696\\n22.9 Web套接字  704\\n第三部分 JavaScript核心参考\\nJavaScript核心参考  711\\n第四部分 客户端JavaScript参考\\n客户端JavaScript参考  847\",\"pages\":\"1004\",\"images\":{\"small\":\"https://img3.doubanio.com\\/spic\\/s8958854.jpg\",\"large\":\"https://img3.doubanio.com\\/lpic\\/s8958854.jpg\",\"medium\":\"https://img3.doubanio.com\\/mpic\\/s8958854.jpg\"},\"alt\":\"https:\\/\\/book.douban.com\\/subject\\/10549733\\/\",\"id\":\"10549733\",\"publisher\":\"机械工业出版社华章公司\",\"isbn10\":\"7111376617\",\"isbn13\":\"9787111376613\",\"title\":\"JavaScript权威指南(第6版)\",\"url\":\"https:\\/\\/api.douban.com\\/v2\\/book\\/10549733\",\"alt_title\":\"JavaScript: The Definitive Guide, Sixth Edition\",\"author_intro\":\"David Flanagan是一名程序员，也是一名作家，它的个人网站是http:\\/\\/davidflanagan.com。他在O'Reilly出版的其他畅销书还包括《JavaScript Pocket Reference》、《The Ruby Programming Language》，以及《Java in a Nutshell》。David毕业于麻生理工学院，获得计算机科学与工程学位。他和妻子和孩子一起生活在西雅图和温哥华之间的美国太平洋西北海岸。\",\"summary\":\"本书是程序员学习核心JavaScript语言和由Web浏览器定义的JavaScript API的指南和综合参考手册。\\n第6版涵盖HTML 5和ECMAScript 5。很多章节完全重写，以便与时俱进，紧跟当今的最佳Web开发实践。本书新增章节描述了jQuery和服务器端JavaScript。\\n本书适合那些希望学习Web编程语言的初、中级程序员和希望精通JavaScript的JavaScript程序员阅读。\",\"series\":{\"id\":\"697\",\"title\":\"博文视点O'reilly系列\"},\"price\":\"139.00元\"}";

        JsonNode jsonNode = StringConverter.converterToJsonNode(s);

        Iterator<JsonNode> l = jsonNode.elements();
        Map<String, JsonNode> map = new HashMap<>();
        while (l.hasNext()) {
            JsonNode m = l.next();
            System.out.println(m);
        }

//        System.out.println(map.get("title").asText());




//        long l1 = System.currentTimeMillis();
//        JsonNode jsonNode = StringConverter.converterToJsonNode(isbn);
//        System.out.println(StringConverter.converterToString(jsonNode, "pubdate"));
//        System.out.println(StringConverter.converterToString(jsonNode, "image"));
//        System.out.println(StringConverter.converterToString(jsonNode, "publisher"));
//        System.out.println(StringConverter.converterToString(jsonNode, "title"));
//        System.out.println(StringConverter.converterToString(jsonNode, "summary"));
//        System.out.println(StringConverter.converterToString(jsonNode, "price"));
//        System.out.println(StringConverter.arrayConverterToString(jsonNode, "author"));
//        System.out.println(StringConverter.arrayConverterToString(jsonNode, "translator"));
//        long l2 = System.currentTimeMillis();
//        System.out.println(l2 - l1);

    }
}
