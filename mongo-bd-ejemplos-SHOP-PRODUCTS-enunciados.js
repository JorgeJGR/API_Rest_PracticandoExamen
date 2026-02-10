// ***************************
// SHOP - PRODUCTS 
// ***************************

/* ************************************************

insertOne  (de un documento)
insertMany (de un array de documentos)

find()
countDocuments()
find() $and, $or, ...
find() $gte, $gt, $lte, $lt, $eq, ...

aggregate() 
aggregate() $group
aggregate() $sum, $avg, $min, $max
aggregate() $match, $group
aggregate() $group, $match
aggregate() $project

find() $in, $all, $size, ...

deleteOne
deleteMany

updateOne
updateMany
	$set, $unset
	$inc, $mul
	$pull, $push
	
***************************************************** */

use shop

db.products.drop()

 
//	INSERT (de un documento)

db.products.insertOne({
		art: "A01",
		descrip: "Uno",
		categoria: "C1",
		colores: ["blanco", "negro", "gris"],
		dimensiones: {
			largo: 4, 
			ancho: 4, 
			espesor: 3
		},
		precio: 15.05
	})
	

//	INSERT (de un array de documentos)

db.products.insertMany([
	{
		art: "A02",
		descrip: "Dos",
		categoria: "C1",
		colores: ["blanco", "gris", "rojo"],
		dimensiones: {
			largo: 4,
			ancho: 10,
			espesor: 2
		},
		precio: 25.95
	},
	{
		art: "A03",
		descrip: "Tres",
		categoria: "C1",
		colores: ["rojo", "gris", "verde"],
		dimensiones: {
			largo: 5,
			ancho: 5,
			espesor: 3
		},
		precio: 30.25
	},
	{
		art: "A04",
		descrip: "Cuatro",
		categoria: "C2",
		colores: ["verde","rojo"],
		dimensiones: {
			largo: 6,
			ancho: 8,
			espesor: 4
		},
		precio: 18.45
	}
])

 
//	FIND
//	Mostrar art, descrip y precio
//	de productos con precio entre 10 y 30 euros

db.products.find( 
	{ precio: { $gte: 10, $lte: 30}},
	{ art:1, descrip:1, precio:1, _id:0 }
)


//  FIND, SORT y LIMIT
//  Mostrar art, descrip y precio
//  de productos los 3 productos más caros (ordenando por precio descendente)

db.products.find( 
	{ },
	{ art:1, descrip:1, precio:1, _id:0 }
).sort({precio: -1}).limit(3)


//  COUNT
//  ¿Cuántos productos cuestan más de 20€?

db.products.countDocuments( 
	{ precio: {$gt: 20}}
)

//  AGGREGATE
//  ¿Cuál es el precio del artículo más barato de cada categoría?

 db.products.aggregate( 
	{ $group: { 
		_id: "$categoria", menor: { $min: "$precio" } 
		} 
	} 
)

//  AGGREGATE
//  ¿Cuántos productos hay de cada categoría?

db.products.aggregate(
  {
	  $group:{ 
			_id: "$categoria", total: { $sum: 1 } 
		}
  }
)


//  AGGREGATE
//  ¿Cuántos productos hay de cada categoría que tenga el precio mayor de 17 euros?
db.products.aggregate(
 	{ $match: {precio: { $lt: 17 } } },
       {
	     $group:{ 
		_id: "$categoria", total: { $sum: 1 } 
			 }
       }
)

//  AGGREGATE
//  Mostrar las categorías que tengan más de 2 productos
db.products.aggregate([
  {
    $group: {
      _id: "$categoria",      // Agrupa por categoría
      totalProductos: { $sum: 1 }  // Cuenta los productos de cada categoría
    }
  },
  {
    $match: {
      totalProductos: { $gt: 2 } // Solo categorías con más de 2 productos
    }
  }
])


//  AGGREGATE - Para campos calculados
//  $concat, $toUpper, $add, $divide, $mod, $multiply, $substract
//  Mostrar art, descrip, precio y dto, siendo dto el 10% del precio
db.products.aggregate(
  {
	$project: {
		_id:0,
		art:1,
		descrip:1, 
		precio:1,
		nombre: {
            $concat: [
                "$art",
				"-",
                "$descrip"
            ]
        },
        descripX: {
            $toUpper: "$descrip"
        },
		oferta: {
            $add: [
                "$precio",
                -5
            ]
        },
		dto: {
            $multiply: [
                "$precio",
                0.10
            ]
        }
	}
  }
)


//  ARRAYS 	
//  Mostrar art, descrip y colores
//  de los productos de color verde

db.products.find(
	{ colores: "verde"},
	{ art:1, descrip:1, colores:1, _id:0 }
)


//  ARRAYS 	
//  Mostrar art, descrip y colores
//  de los productos de color verde O rojo (es decir, que contengan alguno de los dos)

db.products.find( 
	{ $or: [ 
			{ colores: "verde"},
			{ colores: "rojo"},
		]
	},
	{ art:1, descrip:1, colores:1, _id:0 }
)


db.products.find(
	{ colores: { $in: [ "verde", "rojo" ] } },
	{ art:1, descrip:1, colores:1, _id:0 }
)

//  ARRAYS 	
//  Mostrar art, descrip y colores
//  de los productos de color verde Y rojo (es decir, que contengan los dos)

db.products.find( 
	{ $and: [ 
			{ colores: "verde"},
			{ colores: "rojo"},
		]
	},
	{ art:1, descrip:1, colores:1, _id:0 }
)


db.products.find(
  { colores: { $all: ["verde", "rojo"] } },
  { art: 1, descrip: 1, colores: 1, _id: 0 }
)


//  ARRAYS 	
//  Mostrar art, descrip, percio 
//  de los productos que se fabrican en tres colores 

db.products.find(
  { colores: { $size: 3 } },
  { art: 1, descrip: 1, precio: 1, _id: 0 }
)

//  DOCUMENTOS EMBEBIDOS  // Las comillas serán necesarias 
//	Mostrar art, descrip y dimensiones
//	de los productos con espesor de 3 cm
 
db.products.find(
  { "dimensiones.espesor": 3 },
  { art: 1, descrip: 1, dimensiones: 1, _id: 0 }
)


//	INSERT (de un documento)

db.products.insertOne({
		art: "A05",
		descrip: "Cinco",
		categoria: "C2",
		colores: ["rojo", "negro", "gris"],
		dimensiones: {
			largo: 2, 
			ancho: 2, 
			espesor: 3
		},
		precio: 10.20
	})
	
db.products.insertOne({art: "A05",descrip: "Cinco",categoria: "C2",colores: ["rojo", "negro", "gris"],dimensiones: {largo: 2, ancho: 2,espesor: 3},precio: 10.20})
	


//  DELETE

db.products.count()

db.products.deleteOne({art: "A05"})

db.products.count()


//  UPDATE
//  Actualiza el precio del artículo A01 a 26.30

db.products.updateOne(
	{art: "A01"},
	{$set:{precio:26.30}}
)

db.products.find(
	{art: "A01"},
	{art:1, descrip:1, precio:1, _id:0}
)

//  UPDATE
//  Elimina el campo categoria del artículo A03

db.products.find(
	{art: "A03"},
	{_id:0}
)

db.products.updateOne(
	{art: "A03"},
	{$unset: {categoria:1} }
)

db.products.find(
	{art: "A03"},
	{_id:0}
)

//  UPDATE
//  Aumenta el precio de todos los artículos en 5 euros

db.products.find(
	{ },
	{_id:0}
)

db.products.updateMany(
	{},
	{$inc: {precio:5} }
)

db.products.find(
	{ },
	{_id:0}
)

//  UPDATE
//  Aumenta el precio del artículo A03 en un 50%

db.products.find(
	{art: "A03"},
	{_id:0}
)

db.products.updateOne(
	{art: "A03"},
	{$mul: {precio:1.5} }
)

db.products.find(
	{art: "A03"},
	{_id:0}
)

//  UPDATE
//  Elimina del artículo A03 el color verde porque ya no se fabrica

db.products.find(
	{art: "A03"},
	{_id:0}
)

db.products.updateOne(
	{art: "A03"},
	{$pull: {colores:"verde"} }
)

db.products.find(
	{art: "A03"},
	{_id:0}
)

//  UPDATE
//  Añadir al artículo A03 el color blanco

db.products.find(
	{art: "A03"},
	{_id:0}
)

db.products.updateOne(
	{art: "A03"},
	{$push: {colores:"blanco"} }
)

db.products.find(
	{art: "A03"},
	{_id:0}
)



