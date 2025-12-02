package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryAnnotationTransformer implements IAnnotationTransformer {

    private static final Logger log = LoggerFactory.getLogger(RetryAnnotationTransformer.class);

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

        // Newer TestNG uses getRetryAnalyzerClass(), NOT getRetryAnalyzer()
        if (annotation.getRetryAnalyzerClass() == null) {

            annotation.setRetryAnalyzer(RetryAnalyzer.class);

            log.debug("RetryAnalyzer applied to: {}.{}()",
                    testClass != null ? testClass.getSimpleName() : "UnknownClass",
                    testMethod != null ? testMethod.getName() : "UnknownMethod");

        } else {

            log.debug("Retry analyzer already defined for {}.{}() — skipping",
                    testClass != null ? testClass.getSimpleName() : "UnknownClass",
                    testMethod != null ? testMethod.getName() : "UnknownMethod");
        }
    }
}
