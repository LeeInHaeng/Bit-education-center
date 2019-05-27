package config.soundsystem;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.cafe24.springContainer.soundsystem.Index;

@Configuration
@ComponentScan(basePackages="com.cafe24.springContainer.soundsystem")
//@ComponentScan(basePackages= {"com.cafe24.springContainer.soundsystem","com.cafe24.springContainer.videosystem"})
// basePackageClasses에 지정한 클래스가 속한 패키지를 스캐닝
//@ComponentScan(basePackageClasses=Index.class)
public class CDPlayerConfig {

}
