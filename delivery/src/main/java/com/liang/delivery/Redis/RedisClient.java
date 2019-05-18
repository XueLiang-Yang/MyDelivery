package com.liang.delivery.Redis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

 /*配置一些常用的Redis操作*/


public class RedisClient {
    private JedisPool jedisPool;
    public final static String MailQueue = "mailQueue";

    public void set(String key,Object value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key,value.toString());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
    }
    public void del(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
    }

    //设置过期时间

    public void setWithExpireTime(String key,String value,int exptime){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //NX：只在键不存在的时候设置，XX：只在键存在的时候设置
            jedis.set(key,value,"NX","EX",exptime);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
    }

    public String get(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    //LPOP操作 队列出栈
    public <T> T LPOP(String key,Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return JSON.parseObject(jedis.lpop(key),clazz);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }
    //LPOP操作 队列出栈
    public String LPOP(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpop(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }
    //LPUSH操作 队列入栈
    public boolean LPUSH(String key,String value){
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = jedisPool.getResource();
            long l = jedis.lpush(key,value);
            result = l==1L?!result:result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return result;
    }
    //LPUSH操作 多value入栈
    public boolean LPUSH(String key, Set<String> values){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            for(String str:values){
                long l = jedis.lpush(key, str);
                if(l==0){
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return true;
    }
    //LRANGE 返回一个List<String>的集合
    public List<String> LRANGE(String key,long start,long end){
        Jedis jedis = null;
        List<String> list = null;
        try {
            jedis = jedisPool.getResource();
            list = jedis.lrange(key,start,end);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return list;
    }
    //LLEN操作 返回该list的长度
    public long LLEN(String key){
        Jedis jedis = null;
        long len = 0;
        try {
            jedis = jedisPool.getResource();
            len = jedis.llen(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return len;
    }

    //避免强制转换类型出错，这里还需要转换的对象Class作为形参。
    public <T> T HGET(String key,String field,Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return JSON.parseObject(jedis.hget(key,field),clazz);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public Object HSET(String key,String field,Object value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hset(key,field, JSON.toJSONString(value));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
