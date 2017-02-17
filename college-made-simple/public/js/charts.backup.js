window.onload = function () {
    var chart1 = new CanvasJS.Chart("chartContainer1", {

      title:{
        text: "Income"              
      },
      data: [//array of dataSeries              
        { //dataSeries object

         /*** Change type "column" to "bar", "area", "line" or "pie"***/
         type: "pie",
         dataPoints: [
         { label: "Loans", y: 18 },
         { label: "Grants", y: 29 },
         { label: "Parents", y: 40 },                                    
         { label: "Job", y: 34 },
         { label: "Scholarships", y: 24 },
         { label: "Other", y: 15}
         ]
       }
       ]
     });
    var chart2 = new CanvasJS.Chart("chartContainer2", {

      title:{
        text: "Spending"              
      },
      data: [//array of dataSeries              
        { //dataSeries object

         /*** Change type "column" to "bar", "area", "line" or "pie"***/
         type: "pie",
         dataPoints: [
         { label: "Books", y: 14 },
         { label: "Food", y: 24 },
         { label: "Rent", y: 53 },                                    
         { label: "Utilities", y: 12 },
         { label: "Other", y: 33 }
         ]
       }
       ]
     });

    chart1.render();
    chart2.render();
  }
class shift extends HTMLElement {...}
window.customElements.define('shift', shifts);