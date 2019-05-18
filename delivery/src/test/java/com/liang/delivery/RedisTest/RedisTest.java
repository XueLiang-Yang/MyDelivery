/*
package com.liang.delivery.RedisTest;

import com.liang.delivery.Entity.GroupsEntity;
import com.liang.delivery.Entity.InformationEntity;
import com.liang.delivery.Redis.JedisProperties;
import com.liang.delivery.Redis.RedisClient;
import com.liang.delivery.Service.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisClient redisClient;
    @Resource
    private JedisProperties jedisProperties;

    @Test
    public void setJedisPropertiesTest(){
        System.out.println(jedisProperties);
    }
    @Test
    public void listTest(){
        Set<String> mails = new HashSet<String>();
        mails.add("406171296@qq.com");
        mails.add("390079246@qq.com");
        redisClient.LPUSH("group1",mails);
        redisClient.LPUSH("MailQueue","group1");
    }
    @Test
    public void RedisLinkTest() {
        String test = "123";
        redisClient.set("test",test);
        System.out.println(redisClient.get("test"));
    }
    @Test
    public void RedisHSET_HGET_Test() {
        int id = 1;
        InformationEntity informationEntity = new InformationEntity();
        informationEntity.setId(1);
        informationEntity.setEnclosure("null");
        informationEntity.setInfoBody("fastjson这一工具包帮助我们进行java对象和json格式的字符串之间的相互转换。对象到字符串的过程，我们称之为序列化；反之，我们称为反序列化。\n" +
                "\n" +
                "现在我们就来谈谈fastjson提供的反序列化方法，本篇只讨论按照指定的字节码返回相应对象的的反序列化方法，该方法有多种重载形式，按照重叠构造的模式设计。常用的入口为：JSON.parseObject(String text, Class<T> clazz)，其调用链为：\n" +
                "\n" +
                "JSON.parseObject(String text, Class<T> clazz) --> parseObject(String text, Class<T> clazz, Feature... features) --> parseObject(String input, Type clazz, ParserConfig config, int featureValues, Feature... features) --> parseObject(String input, Type clazz, ParserConfig config, ParseProcess processor, int featureValues, Feature... features)\n" +
                "\n" +
                "这样最终实际调用的方法及其参数值为：parseObject(input, clazz, ParserConfig.getGlobalInstance(), null, DEFAULT_PARSER_FEATURE, new Feature[0])。\n" +
                "\n" +
                "我们关注下ParserConfig.getGlobalInstance()，每次调用返回同一个ParserConfig对象。这样其实保证了以JSON.parseObject(String text, Class<T> clazz)为入口的场景下，ParserConfig对象的全局唯一，即所谓的单例。\n" +
                "\n" +
                "我们看看ParserConfig对象在fastjson反序列化过程中的作用：\n" +
                "\n" +
                "作用一：维护了常用类型和反序列化器之间的对应关系，存放到IdentityHashMap<Type, ObjectDeserializer>中，并可通过getDeserializer(Type type)方法获得对象反序列化器ObjectDeserializer；对于非预定义好的类型，拿到该类型的反序列化器的同时，并建立该类型和相应反序列化器的对应关系，存放到IdentityHashMap<Type, ObjectDeserializer>中，以便后续直接使用；\n" +
                "\n" +
                "作用二：创建字段反序列化器FieldDeserializer，而这些FieldDeserializer会维护到ObjectDeserializer的IdentityHashMap<String, FieldDeserializer>中，其中key为字段名称。\n" +
                "\n" +
                "重点关注FieldDeserializer的生成，通过源码分析，通常情况下会调用ASMDeserializerFactory.getInstance().createFieldDeserializer(parserConfig, clazz, fieldInfo)生成字段反序列化器。\n" +
                "\n" +
                "if (fieldClass == int.class || fieldClass == long.class || fieldClass == String.class) {\n" +
                "            return createStringFieldDeserializer(mapping, clazz, fieldInfo);\n" +
                "        }\n" +
                "\n" +
                "通过上面createFieldDeserializer中的源码可以看出，针对int、long和String类型做了特殊处理，进一步分析发现其内部利用asm字节码增加技术对IntegerFieldDeserializer、LongFieldDeserializer以及StringFieldDeserializer做了扩展，动态生成了新的类。\n" +
                "\n" +
                "类名为：String name = \"Fastjson_ASM__Field_\" + clazz.getSimpleName();\n" +
                "        name += \"_\" + fieldInfo.getName() + \"_\" + seed.incrementAndGet();（注意seed此种场景下是单例的）\n" +
                "\n" +
                "该类主要是新增了setValue()方法，应该是用来对字段进行赋值操作的（PS：关于对象序列化和字段序列化器的内部处理逻辑有机会可进一步分析研究）\n" +
                "\n" +
                "综上：针对保留了永久代的jvm，对于如上三种类型的字段，在创建FieldDeserializer时会动态生成新的类，造成jvm加载的类的数目上升，永久代内存的增加。当然通常情况，一个项目中需要反序列化的类是有限的，并且因为常用情况下ParseConfig是单例，相应字段对应的字段序列化器类生成一份后就不在重复生成了，永久代内存通常情况下也就不会溢出。\n" +
                "\n" +
                "JSON.parseObject ( reqMsg, ReqMsgDto.class, new ParserConfig() , JSONObject.DEFAULT_PARSER_FEATURE )");
        informationEntity.setInfoTitle("This is a title");
        informationEntity.setUserGroup(1);
       redisClient.HSET("info"+id,"INFO",informationEntity);

       InformationEntity info = null;
        info =   redisClient.HGET("info"+id,"INFO",InformationEntity.class);
        System.out.println(info);
    }
}
*/
