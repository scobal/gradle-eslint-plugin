/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.scobal.eslint

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.language.base.plugins.LifecycleBasePlugin

import java.nio.file.Files
import java.nio.file.Paths

class ESLintPlugin implements Plugin<Project> {

    private static final String ESLINTIGNORE_FILE = '.eslintignore'
    private static final String WINDOWS_EXECUTABLE_SUFFIX = '.cmd'

    @Override
    void apply(Project project) {

        def esLintPluginConvention = new ESLintPluginConvention(project)
        project.convention.plugins.eslint = esLintPluginConvention

        def esLintTask = project.tasks.register('eslint') {
            group = LifecycleBasePlugin.VERIFICATION_GROUP
            description = 'Runs ESLint.'

            if (new File(project.projectDir, ESLINTIGNORE_FILE).exists()) {
                inputs.file(ESLINTIGNORE_FILE)
            }

            outputs.cacheIf { true }

            doLast {
                project.exec {
                    executable determineExecutable(esLintPluginConvention)
                    args determineArguments(esLintPluginConvention)
                    ignoreExitValue = esLintPluginConvention.ignoreExitValue
                }
            }
        }

        project.afterEvaluate {
            esLintTask.configure {
                inputs.files(esLintPluginConvention.inputs)

                if (!esLintPluginConvention.noEslintrc) {
                    def eslintrcFiles = project.fileTree(dir: project.projectDir, include: '.eslintrc.*')
                    if (!eslintrcFiles.empty) {
                        inputs.files(eslintrcFiles)
                    }
                }

                outputs.file(esLintPluginConvention.outputFile)
            }
        }

        project.tasks.named('check') {
            dependsOn(esLintTask)
        }
    }

    private static String determineExecutable(ESLintPluginConvention convention) {

        if (convention.yarnPath == null) {
            return convention.getExecutable()
        }

        def executablePath = Paths.get(convention.yarnPath)
        if (Files.isDirectory(executablePath)) {
            executablePath = executablePath.resolve('yarn')
        }

        def executable = executablePath.toString()
        if (Os.isFamily(Os.FAMILY_WINDOWS) && !executable.endsWith(WINDOWS_EXECUTABLE_SUFFIX)) {
            executable += WINDOWS_EXECUTABLE_SUFFIX
        }

        executable
    }

    private static List<String> determineArguments(ESLintPluginConvention convention) {

        if (convention.yarnPath == null) {
            return convention.getArguments()
        }

        [] + ESLintPluginConvention.ESLINT + convention.getArguments()
    }
}

