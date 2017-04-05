"use strict";

module.exports = function(grunt) {
  grunt.util.linefeed = '\n';
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    dirs: {
      dest: 'src/main/resources/static',
      buildDest: 'build/resources/main/static'
    },

    bower: {
      install: {
        options: {
          install: true,
          copy: true,
          targetDir: './external/js',
          cleanTargetDir: true
        }
      }
    },

    uglify: {
      dist: {
        files: {
          'src/main/resources/static/js/external.js': ['src/main/resources/static/js/external.js']
        },
        options: {
          mangle: true
        }
      }
    },

    concat: {
     extJs: {
       src: ['external/js/jquery/jquery.js',
             'external/js/bootstrap/bootstrap.js',
             'external/js/angular/angular.js',
             'external/js/angular-resource/angular-resource.js',
             'external/js/angular-ui-router/angular-ui-router.js',
             'external/js/ngstorage/ngStorage.js'],
       dest: '<%= dirs.dest %>/js/external.js'
     },
     appJs: {
       src: ['src/main/js/app.js',
             'src/main/js/services/*.js',
             'src/main/js/controllers/**/*.js'],
       dest: '<%= dirs.dest %>/js/simple-blog-client.js'
     },
     appJsDev: {
       src: ['src/main/js/app.js',
             'src/main/js/services/*.js',
             'src/main/js/controllers/**/*.js'],
       dest: '<%= dirs.buildDest %>/js/simple-blog-client.js'
     },
     css: {
       src: ['external/js/bootstrap-css/bootstrap.min.css',
             'src/main/resources/static/css/own.css'],
       dest: '<%= dirs.dest %>/css/style.css'
     },
     cssDev: {
       src: ['external/js/bootstrap-css/bootstrap.min.css',
             'src/main/resources/static/css/own.css'],
       dest: '<%= dirs.buildDest %>/css/style.css'
     }
    },

    watch: {
      dev: {
        files: ['gruntfile.js', 'src/main/js/**/*.*', 'src/main/resources/static/css/own.css', 'src/main/resources/static/index.html', 'src/main/resources/static/views/*.*'],
        tasks: ['concat:appJs', 'concat:appJsDev', 'concat:css', 'concat:cssDev'],
        options: {
          atBegin: true
        }
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-htmlmin');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-bower-task');

  grunt.registerTask('build', ['concat:appJs', 'concat:css']);
  grunt.registerTask('buildFull', ['bower', 'concat', 'uglify:dist']);
  grunt.registerTask('dev', ['buildFull', 'watch:dev']);
};