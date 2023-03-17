
//简繁切换
function s22t(){
    document.body.innerHTML=s2t(document.body.innerHTML);
}
function t22s(){
    document.body.innerHTML=t2s(document.body.innerHTML);
}
function toggle_simplified(e){
    if(e.innerText=="繁"){
        e.innerText="简"
        s22t()
    }else{
        e.innerText="繁"
        t22s()
    }
}
// <a href="javascript:s22t()">繁体中文</a>
// <a href="javascript:t22s()">简体中文</a>

//返回顶部
$(".back_top").hide()
$(window).scroll((e)=>{
    var now=$(window).scrollTop().toFixed(1)
    var max=(document.body.scrollHeight-document.body.clientHeight).toFixed(1)
    var ratio=(now/max*100).toFixed(1)+"%"
    var ratio_number=parseInt(ratio)
    if(ratio_number>5||now>100)
        $(".back_top").show()
    else
        $(".back_top").hide()
})

//懒加载
new LazyLoad($(".lazyload"));
$(document).ready(()=>{
    new LazyLoad($(".lazyload_on_page_ready"));
})
