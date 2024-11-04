package forex.trainer.ac.za.controller;

import forex.trainer.ac.za.model.event.CourseEvent;
import forex.trainer.ac.za.service.event.CourseEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event")
public class CourseEventController {

    @Autowired
    CourseEventService courseEventService;

    @PostMapping("create/{courseId}")
    public ResponseEntity<CourseEvent> create(@RequestBody CourseEvent courseEvent, @PathVariable("courseId") String courseId) {
        return new ResponseEntity<>(courseEventService.createEvent(courseEvent,courseId),HttpStatus.OK);
    }

    @GetMapping("{courseId}")
    public ResponseEntity<List<CourseEvent>> getAll(@PathVariable String courseId) {
        return new ResponseEntity<>(courseEventService.getEventsForCourse(courseId),HttpStatus.OK);
    }

    @PutMapping("cancel/{eventId}")
    public ResponseEntity<CourseEvent> cancelEvent(@PathVariable String eventId){
        return new ResponseEntity<>(courseEventService.cancelEvent(eventId), HttpStatus.OK);
    }
}
