package core;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Annotates tests with RetryAnalyzer automatically if they don't already specify one.
 */
public class RetryAnnotationTransformer implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // set retry analyzer for all tests (won't affect tests that explicitly set another analyzer in their annotation)
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
