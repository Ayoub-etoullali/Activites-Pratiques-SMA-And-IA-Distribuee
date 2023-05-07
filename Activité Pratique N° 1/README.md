# TP6: Ventes avec Structured Streaming en pyhton

## Exercice 1 : Word Count

```
>>> from pyspark.sql import SparkSession
>>> from pyspark.sql.functions import explode
>>> from pyspark.sql.functions import split
>>> dfLines=spark.readStream.format("socket").option("host","localhost").option("port",8888).load()
>>> dfWords=dfLines.select(explode(split(dfLines["value"]," ")).alias("words"))
>>> dfWordCount=dfWords.groupBy("words").count()
>>> dfWordCount.writeStream.format("console").outputMode("update").trigger(processingTime='5 seconds').start().awaitForTermination()
```

## Exercice 2 :
![image](https://user-images.githubusercontent.com/92756846/224802856-e9fefc64-4178-4037-b94b-8b48dfdc1439.png)
  
  #### Fichier "ventes.txt"
  ![image](https://user-images.githubusercontent.com/92756846/225772439-ea4eb6c8-1472-40a0-b109-bf214532374b.png)

  ### Question 1 :

  ### Question 2 : 

#### Demo :
<div align="center">
       <p>
       <sup>  <strong>Vidéo -</strong> Ventes avec Structured Streaming en pyhton</sup>
       </p>
</div>

<kbd>Enjoy Code</kbd> 👨‍💻
