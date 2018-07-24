package demo

import com.android.build.api.transform.*
import com.android.build.api.transform.QualifiedContent.ContentType
import com.android.build.api.transform.QualifiedContent.Scope
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Project

class PathTransform extends Transform {

    Project project

    public PathTransform(Project project) {
        this.project = project
    }

    @Override
    void transform(
            Context context,
            Collection<TransformInput> inputs,
            Collection<TransformInput> referencedInputs,
            TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {

        System.out.println("=======================================doPathTransform{ context=${context}, inputs=${inputs}, referencedInputs=${referencedInputs}, outputProvider=${outputProvider}, isIncremental=${isIncremental}")

        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }

    @Override
    String getName() {
        return PathTransform.simpleName
    }

    @Override
    Set<ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }
}