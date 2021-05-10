function HdHighChart() {
}
HdHighChart.credits={};
HdHighChart.title={};
HdHighChart.xAxis={};
HdHighChart.yAxis={};
HdHighChart.series;
HdHighChart.exporting;
HdHighChart.init = function (cfg) {
    var ids=cfg.type.toString().split(",");
    if(ids.length>0){
    for(var i=0;i<ids.length;i++){
        var chart= cfg.chart || {renderTo: ids[i], type: ids[i]};
        $("#"+ids[i]).css("display","block");
        HdHighChart.init_chartProperty(cfg);
        var option = {
        chart: chart,
        credits: HdHighChart.credits,
        title: HdHighChart.title,
        exporting:HdHighChart.exporting,
        xAxis: HdHighChart.xAxis,
        yAxis: HdHighChart.yAxis,
        series: HdHighChart.series
    };
    var highChart = new Highcharts.Chart(option);
    }
    }
}
HdHighChart.init_chartProperty = function (cfg) {
    HdHighChart.credits = cfg.credits || {enabled: false};
    HdHighChart.title = cfg.title || {text: 'title'};
    HdHighChart.xAxis = cfg.xAxis || {title: {text: 'x轴'}};
    HdHighChart.yAxis = cfg.yAxis || {title: {text: 'y轴'}};
    HdHighChart.series = cfg.series ||[{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }];
    HdHighChart.exporting=cfg.exporting||{
                    //enabled:true,默认为可用，当设置为false时，图表的打印及导出功能失效
                    buttons:{    //配置按钮选项
                        printButton:{    //配置打印按钮
                            width:50,
                            symbolSize:20,
                            borderWidth:2,
                            borderRadius:0,
                            hoverBorderColor:'red',
                            height:30,
                            symbolX:25,
                            symbolY:15,
                            x:-200,
                            y:20
                        },
                        exportButton:{    //配置导出按钮
                            width:50,
                            symbolSize:20,
                            borderWidth:2,
                            borderRadius:0,
                            hoverBorderColor:'red',
                            height:30,
                            symbolX:25,
                            symbolY:15,
                            x:-150,
                            y:20
                        }
                    },
                    filename:HdHighChart.title.text,//导出的文件名
                    width:800    //导出的文件宽度
                  };
    if(cfg.url!=null){
    $.ajax({
            url: cfg.url,
            contentType: "application/json",
            dataType: "json",
            type: "GET",
            async: false,
            success: function (data) {
                    if(data.categories!=null)
                        HdHighChart.xAxis.categories = data.categories;
                    else{
                        var categories = [];
                        var d = data.series[0].data;
                        if(d[0].toString().split(",").length>0){
                        for (var i = 0; i < d.length; i++) {
                            categories.push(d[i].toString().split(",")[0]);
                        }
                        HdHighChart.xAxis.categories =categories;
                    }
                    }
                    HdHighChart.series=data.series;
                }
            });
        }
}
