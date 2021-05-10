/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function HdHighChart(options) {
    this.series=options.series;
    var chart={};
    if(options.id){
        chart.id=options.id;
    }
    if(options.type){
         charttype=options.type
    }
    if(url){
        var result=getSeriesData(url);
        this.series=result.series;
    }
    
}
HdHighChart.prototype.setSeriesData=function(result){
    this.series=result.series;
}
function getSeriesData(url){
    return [];
}
