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

import org.gradle.api.Project

class ESLintPluginConvention {

    def String executable = 'eslint'
    def List<String> inputs = null
    def String config = null
    def Boolean noEslintrc = null
    def List<String> env = null
    def List<String> ext = null
    def List<String> global = null
    def String parser = null
    def Object parserOptions = null
    def Boolean cache = null
    def String cacheFile = null
    def String cacheLocation = null
    def List<String> rulesDir = null
    def List<String> plugin = null
    def Object rule = null

    def ESLintPluginConvention(Project project) {
		if (Os.isFamily(Os.FAMILY_WINDOWS)) {
			executable += '.cmd'
		}
    }

    def eslint(Closure closure) {
        closure.delegate = this
        closure()
    }

    def List getArguments() {
        def args = []

        if (config != null) {
            args += ['--config', config]
        }
        if (noEslintrc != null) {
            args += ['--no-eslintrc', noEslintrc]
        }
        if (env != null) {
            args += '--env'
            args += env
        }
        if (ext != null) {
            args += '--ext'
            args += ext
        }
        if (global != null) {
            args += '--global'
            args += global
        }
        if (parser != null) {
            args += ['--parser', parser]
        }
        if (parserOptions != null) {
            args += '--parser-options'
            args += parserOptions
        }
        if (cache != null) {
            args += ['--cache', cache]
        }
        if (cacheFile != null) {
            args += ['--cache-file', cacheFile]
        }
        if (cacheLocation != null) {
            args += ['--cache-location', cacheLocation]
        }
        if (rulesDir != null) {
            args += '--rulesdir'
            args += rulesDir
        }
        if (plugin != null) {
            args += '--plugin'
            args += plugin
        }
        if (rule != null) {
            args += '--rule'
            args += rule
        }
        if (inputs != null) {
            args += inputs
        }

        return args
    }
}
