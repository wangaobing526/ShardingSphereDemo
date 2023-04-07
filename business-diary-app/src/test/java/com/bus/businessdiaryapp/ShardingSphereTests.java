package com.bus.businessdiaryapp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sharding.entity.Course;
import com.sharding.mapper.CourseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ShardingSphereTests {
    @Resource
    CourseMapper courseMapper;
    @Test
    public void addCourse(){
        for(int i=0;i<10;i++){
            Course c = new Course();
//            c.setCid(Long.valueOf(i));
            c.setCname("shardingsphere");
            c.setUserId(Long.valueOf(""+(1000+i)));
            c.setCstatus("1");
            courseMapper.insert(c);
        }
    }

    @Test
    public void queryCourseAll(){
        List<Course> courses = courseMapper.selectList(null);
        courses.forEach(course -> System.out.println(course));
    }

    @Test
    public void queryCourse(){
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("user_id");
        List<Course> courses = courseMapper.selectList(wrapper);
        courses.forEach(course -> System.out.println(course));
    }


    @Test
    public void queryOrderRange(){
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.between("cid",848523372789760000L,848523373372768257L);
        List<Course> courses = courseMapper.selectList(wrapper);
        courses.forEach(course -> System.out.println(course));
    }


    @Test
    public void queryCourseByCid(){
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("cid",848523372789760000L);
        Course courses = courseMapper.selectOne(wrapper);
        System.out.println(courses);
    }



}
