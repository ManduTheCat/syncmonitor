package syncMonitor.config.wrapper.DbConfig;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DbConfigConverter {
    public static DbConfig convertToDbConfig(Map<String, Object> map) {
        String dbType = (String) map.get("db");

        if ("tibero".equalsIgnoreCase(dbType)) {
            TiberoConfig tiberoConfig = new TiberoConfig();
            tiberoConfig.setIp((String) map.get("ip"));
            tiberoConfig.setPort(String.valueOf((Integer) map.get("port")));
            tiberoConfig.setId((String) map.get("id"));
            tiberoConfig.setPwd((String) map.get("pwd"));
            tiberoConfig.setUser1((String) map.get("user"));
            tiberoConfig.setDbSid((String) map.get("dbSid"));
            return tiberoConfig;

        } else if ("oracle".equalsIgnoreCase(dbType)) {
            OracleConfig oracleConfig = new OracleConfig();
            oracleConfig.setIp((String) map.get("ip"));
            oracleConfig.setPort(String.valueOf((Integer) map.get("port")));
            oracleConfig.setId((String) map.get("id"));
            oracleConfig.setPwd((String) map.get("pwd"));
            oracleConfig.setUser1((String) map.get("user"));
            oracleConfig.setDbSid((String) map.get("dbSid"));
            return oracleConfig;
        }

        throw new IllegalArgumentException("Unsupported DB type: " + dbType);
    }
    public static HashMap<String, Object> ConverObjectToMap(Object obj){
        try {
            //Field[] fields = obj.getClass().getFields(); //private field는 나오지 않음.
            Field[] fields = obj.getClass().getDeclaredFields();
            HashMap<String, Object> resultMap = new HashMap<String, Object>();
            for(int i=0; i<=fields.length-1;i++){
                fields[i].setAccessible(true);
                resultMap.put(fields[i].getName(), fields[i].get(obj));
            }
            return resultMap;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
