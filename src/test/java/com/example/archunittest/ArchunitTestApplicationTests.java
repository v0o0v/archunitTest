package com.example.archunittest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ArchunitTestApplicationTests {

    @Test
    void controller패키지는service패키지에만의존성을갖는다() {

        JavaClasses jc = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example");

        ArchRule rule = classes()
                .that().resideInAnyPackage("..controller..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("..service..")
                ;

        rule.check(jc);
    }

    @Test
    void controller패키지는service패키지에만의존성을갖는다2() {

        JavaClasses jc = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example");

        ArchRule rule = classes()
                .that().resideInAnyPackage("..controller..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("..service..","java..", "javax..", "org.springframework..")
                ;

        rule.check(jc);
    }

    @Test
    void service패키지는controller와persistence패키지에서만의존성을갖는다() {

        JavaClasses jc = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example");

        ArchRule rule = classes()
                .that().resideInAnyPackage("..service..")
                .should().onlyHaveDependentClassesThat().resideInAnyPackage("..controller..","..persistence..","..service..")
                ;

        rule.check(jc);
    }

    @Test
    void controller패키지는persistence패키지에의존성을갖지않는다() {

        JavaClasses jc = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example");

        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..controller..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("..persistence..")
                ;

        rule.check(jc);
    }

    @Test
    void layerTest() {

        JavaClasses jc = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example");

        Architectures.LayeredArchitecture arch = layeredArchitecture()
                // Define layers
                .layer("Controller").definedBy("..controller..")
                .layer("Service").definedBy("..service..")
                .layer("Persistence").definedBy("..persistence..")
                // Add constraints
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Persistence").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller","Persistence")
                ;

        arch.check(jc);
    }

}
