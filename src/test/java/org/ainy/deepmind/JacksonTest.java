package org.ainy.deepmind;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.ainy.deepmind.model.Student;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AINY_uan
 * @description Jackson测试
 * @date 2020-03-14 23:49
 */
public class JacksonTest {

    @Test
    public void ex1() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // 不保留null

        Student student = new Student();

        student.setId(1);
        student.setName("小米");
        student.setAge(6);

        System.out.println("-----------对象转JSON字符串-------------");
        /*
         * 对象转JSON字符串
         */
        System.out.println(objectMapper.writeValueAsString(student));


        System.out.println("-----------Map转JSON字符串-------------");
        /*
         * Map转JSON字符串
         */
        Map<String, Object> map = new HashMap<>();
        map.put("MI", student);
        System.out.println(objectMapper.writeValueAsString(map));

        System.out.println("-----------数组转JSON字符串-------------");
        /*
         * 数组转JSON字符串
         */
        String[] arrays = {"小米", "华为", "三星"};
        System.out.println(objectMapper.writeValueAsString(arrays));

        System.out.println("-----------JSON字符串转对象--------------");
        /*
         * JSON字符串转对象
         */
        String s1 = "{\"id\":1,\"name\":\"小米\",\"age\":6}";
        System.out.println(objectMapper.readValue(s1, Student.class));

        System.out.println("-----------JSON字符串转Map-------------");
        /*
         * JSON字符串转Map
         */
        String s2 = "{\"MI\":{\"id\":1,\"name\":\"小米\",\"age\":6}}";
        System.out.println(objectMapper.readValue(s2, Map.class));

        System.out.println("-----------JSON字符串转对象数组List<Object>------------");
        /*
         * JSON字符串转对象数组List<Object>
         */
        String s3 = "[{\"id\":1,\"name\":\"小米\",\"age\":6}, {\"id\":2,\"name\":\"华为\",\"age\":6}]";
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Student.class);
        List<Student> studentList = objectMapper.readValue(s3, listType);
        System.out.println(studentList);

        System.out.println("-----------JSON字符串转Map数组List<Map<String, Object>>------------");
        /*
         * JSON字符串转Map数组List<Map<String, Object>>
         */
        String s4 = "[{\"MI\":{\"id\":1,\"name\":\"小米\",\"age\":6,\"grade\":null,\"ext\":null,\"balance\":null}}, {\"HW\":{\"id\":2,\"name\":\"华为\",\"age\":6,\"grade\":null,\"ext\":null,\"balance\":null}}]";
        CollectionType listType2 = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, map.getClass());
        List<Map<String, Object>> studentList2 = objectMapper.readValue(s4, listType2);
        System.out.println(studentList2);

        System.out.println("-----------JACKSON默认将对象转换为LinkedHashMap------------");

        String expected = "[{\"name\":\"Logan\"},{\"name\":\"IronMan\"},{\"name\":\"SpiderMan\"}]";
        List<?> arrayList = objectMapper.readValue(expected, ArrayList.class);
        System.out.println(arrayList);

        System.out.println("-----------bean转换为map----------------------------------");
        Student student2 = new Student();
        student2.setId(2);
        student2.setName("华为");
        student2.setAge(11);

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student2);

        List<Map<String, Object>> xxxList = objectMapper.convertValue(students, new TypeReference<List<Map<String, Object>>>() {});

        System.out.println(xxxList);
    }
}
