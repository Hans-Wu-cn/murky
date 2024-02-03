package cn.murky.socketd.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SdEvent {
    String eventName() default "";

}
