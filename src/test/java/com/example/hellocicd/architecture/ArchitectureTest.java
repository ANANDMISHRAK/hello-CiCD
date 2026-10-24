package com.example.hellocicd.architecture; // Keeps architecture tests in their own test package.

import com.tngtech.archunit.core.importer.ImportOption; // Provides options for selecting which compiled classes ArchUnit imports.
import com.tngtech.archunit.junit.AnalyzeClasses; // Tells ArchUnit which production package to inspect.
import com.tngtech.archunit.junit.ArchTest; // Marks a field or method as an architecture rule.
import com.tngtech.archunit.lang.ArchRule; // Represents a rule that can be checked against compiled classes.

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes; // Starts a fluent rule definition for Java classes.

/**
 * Demonstrates a simple architecture convention.
 * Real projects can add rules for controller/service/repository layers and dependency direction.
 */
@AnalyzeClasses( // Configures the package scan performed by ArchUnit.
        packages = "com.example.hellocicd", // Inspects classes under the application's root package.
        importOptions = ImportOption.DoNotIncludeTests.class // Excludes test classes from the production-code architecture scan.
)
class ArchitectureTest { // Declares the class containing architecture rules.

    @ArchTest // Tells ArchUnit to execute this rule during the test run.
    static final ArchRule controllersShouldHaveControllerInTheirName = // Gives the rule a readable name for failure reports.
            classes() // Begins a rule that selects Java classes.
                    .that().resideInAPackage("..controller..") // Selects classes located in any package segment named controller.
                    .should().haveSimpleNameEndingWith("Controller"); // Requires each selected class name to end with Controller.
} // Ends the architecture test class.
