var gulp = require('gulp'),
    sass = require('gulp-sass'),
    concat = require('gulp-concat'),
    clean = require('gulp-clean'),
    watch = require('gulp-watch'),
    livereload = require('gulp-livereload');

var paths = {
    scripts: './js/*.js',
    scss: './scss/main.scss',
    allscss: './scss/*.scss'
};

gulp.task('clean', function () {
    return gulp.src('./css/style.css', {read: false})
        .pipe(clean());
});

gulp.task('styles', function () {
    return gulp.src(paths.scss)
        .pipe(sass({
            style: 'compressed',
            errLogToConsole: false,
            onError: function (err) {
                return notify().write(err);
            }
        }))
        .pipe(concat('style.css'))
        .pipe(gulp.dest('./css'));
});

gulp.task('watch', function () {
    livereload.listen();
    gulp.watch(paths.allscss, ['styles']);
});