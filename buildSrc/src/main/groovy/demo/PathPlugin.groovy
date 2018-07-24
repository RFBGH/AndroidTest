package demo

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.AppExtension

class PathPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(new PathTransform(project))

        project.task('transformPath') {
            doLast {
                System.out.println('+++++++++++++++++++++transformPath task')
            }
        }
    }
}