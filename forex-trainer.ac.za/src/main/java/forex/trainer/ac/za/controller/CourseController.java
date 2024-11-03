package forex.trainer.ac.za.controller;


import forex.trainer.ac.za.dtos.cource.UserSubscribe;
import forex.trainer.ac.za.model.course.Course;
import forex.trainer.ac.za.model.course.course_subscriptions.CourseSubscriptions;
import forex.trainer.ac.za.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("course")
public class CourseController
{
    @Autowired
    private CourseService courseService;

    @RequestMapping("view")
    public ResponseEntity<List<Course>> view(){
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Course> getCourse(@PathVariable UUID id) {
        Course course = courseService.getCourse(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    // Create a new course
    @PostMapping("create")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    // Update an existing course
    @PutMapping("update")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(course);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    // Delete a course
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("student/course/subscriptions/{userId}")
    public ResponseEntity<List<CourseSubscriptions>> getCourseSubscriptions(@PathVariable UUID userId) {
        return  new ResponseEntity<>(courseService.getUserCourseSubscriptions(userId), HttpStatus.OK);
    }

    @PostMapping("student/subscribe/course")
    public ResponseEntity<CourseSubscriptions> subscribeCourse(@RequestBody UserSubscribe course) {
        return new ResponseEntity<>(courseService.subscribeCourse(course.getUserAccountId(),course.getCourseId()),HttpStatus.OK);
    }

}
