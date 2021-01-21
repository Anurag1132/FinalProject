var express=require('express'); // express lib
const app=express();
var mysql=require('mysql');// mysql lib

var bodyparser=require('body-parser'); // json data parsing

//mysql connection
var connect=mysql.createConnection({
    host:'localhost',
    user:"root",
    password:"anurag",
    database:'shoponline'
});

connect.connect();// mysql connection test

app.use(bodyparser.json());
app.use(bodyparser.urlencoded({extended:true}));

//wishlist

app.post('/addWishlist/',(req,res,next)=>{

    var data=req.body;
    var product_id=data.product_id;
    var user_name= data.user_name;

// for inserting data
    
            var insert_cmd="insert into wishlist (product_id,user_name) values (?,?)";
                    values=[product_id,user_name];
connect.query(insert_cmd,values,(err,results,fields)=>{

    connect.on('error',(err)=>{
        console.log("[MySQL Error",err);
    });

    console.log("Inserted");
});

connect.query("select * from wishlist where user_name=? order by id desc limit 1",[user_name], function(err,result,fields){
    connect.on('error',(err)=>{
        console.log("[MySQL Error",err);
    });
    
    if(result && result.length){
        res.json(result[0]);
    }
});          
    
});

app.post('/viewWishlist/',(req,res,next)=>{

    var data=req.body;
    var user_name= data.user_name;

// for inserting data
   

            connect.query("select * from wishlist where user_name=? ",[user_name], function(err,result,fields){
                connect.on('error',(err)=>{
                    console.log("[MySQL Error",err);
                });
                
                if(result && result.length){
                    res.json(result);
                }
            });     
     
        
});

var server=app.listen(3060,()=>{   
    console.log("Server running at http://localhost:3060");
});


