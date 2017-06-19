# Gradle ESLint Plugin

This is a Gradle plugin for running [ESLint](http://eslint.org/) as part of your Gradle build.

## Usage

1. Install ESLint via npm: `npm install eslint`
1. Add the following to your build.gradle

        buildscript {
            repositories {
                maven {
                    url "https://plugins.gradle.org/m2/"
                }
            }
            dependencies {
                classpath "gradle.plugin.com.github.scobal.eslint:gradle-eslint-plugin:1.0.4"
            }
        }

        apply plugin: "com.github.scobal.eslint"

1. Configure ESLint to include your Javascript files in your build.gradle

        eslint {
            inputs = ["**/*.js*"]
        }

1. Run `gradle eslint`

## Configuration

The goal of this plugin is to support as fully as possible the command line interface described here:

http://eslint.org/docs/user-guide/command-line-interface

This is a list of the available options for use inside the `eslint` configuration block:


|       Option      | Description |
| ----------------- |------------ |
| `executable` | The path to an eslint executable, eg: `"/usr/bin/eslint"` 
| `inputs` | A list of files, directories and/or globs, eg: `["service.js", "src/controllers/", "**/*.js"]`
| `config` | The path to an eslint configuration file, eg: `"/home/scobal/esconfig"`
| `noEslintrc` |  Boolean to disable the use of .eslintrc, eg: `true`
| `env` | A list of environments as strings, eg: `["environment_1", "environment_2"]`
| `ext` | A list of Javascript file extensions as strings, eg: `[".js", ".jsx"]`
| `global` | A list of global variables as strings, eg: `["global_1", "global_1"]`
| `parser` | The parser to be used, eg: `"espree"`
| `parserOptions` | Specify parser options
| `cache` | Boolean to only check changed files, eg: `true`
| `cacheFile` | The path to an eslint cache file, eg: `"/tmp/eslint_cache/file"`
| `cacheLocation` | The path to an eslint cache file or directory, eg: `"/tmp/eslint_cache"`
| `rulesDir` | A list of directories with additional rules, eg: `["/eslint/rules_1", "/eslint/rules_2"]`
| `plugin` | A list of plugins as strings, eg: `["plugin_1", "plugin_2"]`
| `rule` | Specify rules
| `outputFile` | The path to the output file, eg: `"/home/scobal/report.out"`
| `format` | The format of the output file, eg: `"checkstyle"`

## License

gradle-eslint-plugin is licensed under version 2.0 of the Apache License. See the [LICENSE](https://github.com/scobal/gradle-eslint-plugin/blob/master/LICENSE) file for details.

## Development

[![Build Status](https://travis-ci.org/scobal/gradle-eslint-plugin.svg?branch=master)](https://travis-ci.org/scobal/gradle-eslint-plugin)
