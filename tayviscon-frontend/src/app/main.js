import '../css/main.css'


function removeNavClasses() {
    $('#scope').removeClass('why-scope edu-scope projects-scope academy-scope support-scope community-scope');
    $('.drop-menu').removeClass('active');
    $('.has-menu').removeClass('active');
}
$('#academy-target').mouseenter(function () {
    removeNavClasses();
    $('#scope').addClass('academy-scope');
    $('#academy-items').addClass('active');
});
$('#support-target').mouseenter(function () {
    removeNavClasses();
    $('#scope').addClass('support-scope');
    $('#support-items').addClass('active');
});
$('#why-target').mouseenter(function () {
    removeNavClasses();
    $('#scope').addClass('why-scope');
    $('#why-items').addClass('active');
});
$('#edu-target').mouseenter(function () {
    removeNavClasses();
    $('#scope').addClass('edu-scope');
    $('#edu-items').addClass('active');
});
$('#project-target').mouseenter(function () {
    removeNavClasses();
    $('#scope').addClass('projects-scope');
    $('#project-items').addClass('active');
});
$('#community-target').mouseenter(function () {
    removeNavClasses();
    $('#scope').addClass('community-scope');
    $('#community-items').addClass('active');
});
$('.drop-menu').mouseenter(function () {
    $('this').addClass('active');
});
$('.drop-menu ul').mouseleave(function () {
    removeNavClasses();
});
$('.drop-menu').mouseleave(function () {
    removeNavClasses();
});

//Accessibility
$('#why-hov span').focus(function () {
    removeNavClasses();
    $('#scope').addClass('why-scope');
    $('#why-items').addClass('active');
});
$('#edu-hov span').focus(function () {
    removeNavClasses();
    $('#scope').addClass('edu-scope');
    $('#edu-items').addClass('active');
});
$('#projects-hov span').focus(function () {
    removeNavClasses();
    $('#scope').addClass('projects-scope');
    $('#project-items').addClass('active');
});
$('#academy-hov span').focus(function () {
    removeNavClasses();
    $('#scope').addClass('academy-scope');
    $('#academy-items').addClass('active');
});
$('#support-hov span').focus(function () {
    removeNavClasses();
    $('#scope').addClass('support-scope');
    $('#support-items').addClass('active');
});
$('#community-hov span').focus(function () {
    removeNavClasses();
    $('#scope').addClass('community-scope');
    $('#community-items').addClass('active');
});
$('#logo-focus').focus(function () {
    setTimeout(function () {
        $('#logo-focus').addClass('focused');
    }, 150);
});
$('#logo-focus').blur(function () {
    $(this).removeClass('focused');
});
$('#logo-focus').click(function () {
    $(this).removeClass('focused');
});
$('.drop-menu').focus(function () {
    $('this').addClass('active');
});
$('#logo-focus, #nav-items a, #search').focus(function () {
    removeNavClasses();
});