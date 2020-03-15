package org.ainy.deepmind;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.ainy.deepmind.model.Student;
import org.ainy.deepmind.util.PropertyUtil;
import org.junit.Test;

import java.util.Map;

/**
 * @author AINY_uan
 * @description JSON测试
 * @date 2020-03-15 14:02
 */
public class JsonTest {

    @Test
    public void gson() throws JsonProcessingException {

        Student s = new Student();
        s.setName("小米");
        s.setAge(1);

        System.out.println("----------Gson（不保留null）---------");
        Gson gson = new Gson();
        System.out.println(gson.toJson(s));
        System.out.println("----------Gson（保留null）----------");
        Gson gsonSerializeNull = new GsonBuilder().serializeNulls().create();
        System.out.println(gsonSerializeNull.toJson(s));


        System.out.println("----------ObjectMapper（不保留null）---------");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        System.out.println(objectMapper.writeValueAsString(s));


        System.out.println("----------json-lib（不保留null）---------");
        JsonConfig jsonConfig = new JsonConfig();
        PropertyFilter filter = (object, fieldName, fieldValue) -> null == fieldValue;
        jsonConfig.setJsonPropertyFilter(filter);
        System.out.println(JSONObject.fromObject(s, jsonConfig).toString());
    }

    @Test
    public void json() {

        JSONObject o0 = JSONObject.fromObject(PropertyUtil.getProperty("s0"));
        JSONObject o00 = JSONObject.fromObject(o0.get("txninfo"));
        System.out.println("responsecode:" + o00.get("responsecode"));

        System.out.println("------------------------------------------------------------");
        JSONObject o1 = JSONObject.fromObject(PropertyUtil.getProperty("s1"));
        System.out.println("msgType: " + o1.get("merName"));
        JSONObject o2 = o1.getJSONObject("appPayRequest");
        System.out.println("qrCode: " + o2.get("qrCode"));

        Gson gson = new Gson();
        String test = "{\\\"biz_no\\\":\\\"20180911111900000000680005279542\\\",\\\"merchant_id\\\":\\\"37020001\\\",\\\"serial_no\\\":\\\"1955926712261279752\\\",\\\"merchant_trade_no\\\":\\\"18091107582000224076147515195392\\\",\\\"distributor_trade_no\\\":null,\\\"submit_time\\\":\\\"2018-09-11 07:58:37\\\",\\\"pay_time\\\":\\\"2018-09-11 07:58:38\\\",\\\"trade_scene\\\":\\\"INTRA_PREPAY\\\",\\\"amount\\\":100,\\\"actual_amount\\\":100,\\\"distributor_preferential\\\":0,\\\"distributor_user_id\\\":\\\"2088112347679686\\\",\\\"distributor_card_no\\\":\\\"2088112347679686\\\",\\\"pos_id\\\":\\\"370020086853\\\",\\\"pos_serial_no\\\":\\\"18091023583000003700200868530004\\\"}";
        Map<String, String> reqData = gson.fromJson(test.replace("\\", ""), new TypeToken<Map<String, String>>() {}.getType());
        System.out.println(reqData);
    }
}
