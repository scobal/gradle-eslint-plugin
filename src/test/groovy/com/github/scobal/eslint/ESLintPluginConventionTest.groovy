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
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.*

import static org.junit.Assert.*

class ESLintPluginConventionTest {

    private Project project
    private ESLintPluginConvention convention

    @Before
    def void setup() {
        project = ProjectBuilder.builder().build()
        convention = new ESLintPluginConvention(project)
    }

    @Test
    def void testDefaults() {
        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            assertEquals("eslint.cmd", convention.getExecutable())
        } else {
            assertEquals("eslint", convention.getExecutable())
        }
        assertNull(convention.getNodePath())
        assertNull(convention.getYarnPath())
        assertEquals(false, convention.getIgnoreExitValue())
        assertEquals(['src'], convention.getInputs())
        assertNull(convention.getConfig())
        assertNull(convention.getNoEslintrc())
        assertNull(convention.getEnv())
        assertNull(convention.getExt())
        assertNull(convention.getGlobal())
        assertNull(convention.getParser())
        assertNull(convention.getParserOptions())
        assertNull(convention.getCache())
        assertNull(convention.getCacheFile())
        assertNull(convention.getCacheLocation())
        assertNull(convention.getRulesDir())
        assertNull(convention.getPlugin())
        assertNull(convention.getRule())
    }

    @Test
    def void testGetArguments_withDefaultValues() {
        assertEquals(['src'], convention.getArguments())
    }

    @Test
    def void testGetArguments_withCustomValues() {
        convention.setInputs(["1.js", "2.js"])
        convention.setConfig("esconfig")
        convention.setNoEslintrc(true)
        convention.setEnv(["env1", "env2"])
        convention.setExt(["ext1", "ext2"])
        convention.setGlobal(["global1", "global2"])
        convention.setParser("parser")
        convention.setParserOptions(["parser_option:1", "parser_option:2"])
        convention.setCache(true)
        convention.setCacheFile("cache_file")
        convention.setCacheLocation("cache_location")
        convention.setRulesDir(["rules_dir1", "rules_dir2"])
        convention.setPlugin(["plugin1", "plugin2"])
        convention.setRule(["rule:1", "rule:2"])
        convention.setFormat("checkstyle")
        convention.setOutputFile("report.out")

        assertEquals(["--config", "esconfig",
                      "--no-eslintrc", true,
                      "--env", "env1", "env2",
                      "--ext", "ext1", "ext2",
                      "--global", "global1", "global2",
                      "--parser", "parser",
                      "--parser-options", "parser_option:1", "parser_option:2",
                      "--cache", true,
                      "--cache-file", "cache_file",
                      "--cache-location", "cache_location",
                      "--rulesdir", "rules_dir1", "rules_dir2",
                      "--plugin", "plugin1", "plugin2",
                      "--rule", "rule:1", "rule:2",
                      "--format", "checkstyle",
                      "--output-file", "report.out",
                      "1.js", "2.js"],
                convention.getArguments())
    }
}
