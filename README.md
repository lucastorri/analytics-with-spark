# Analytics with Spark

Exercises for the book [Advanced Analytics with Spark: Patterns for Learning from Data at Scale](http://www.amazon.com/Advanced-Analytics-Spark-Patterns-Learning/dp/1491912766).

This project also provided a quick way to get *Spark* running without the fuss of downloading files, etc. All you need **should** be already here.


## Packages

  * `chapters`: code for each of the chapters. The is a class for each of with `Chapter` being the name prefix, followed by the chapter number, i.e. `Chapter2`;
  * `console`: a REPL with `SparkContext` provided;
  * `spark`: utilities to help getting *Spark* running.


## Console

The console uses the [Ammonite-REPL](https://lihaoyi.github.io/Ammonite/). To enter it, run:

```bash
./sbt console
```

It will automatically provide a running context (`sc` variable), import some of the *Spark* packages, and the `chapters` package.

Ammonite has way more features than the standard REPL, so I recommend taking a look at their website.


## Running a Specific Chapter

```bash
./sbt 'run-main analytics.chapters.Chapter2'
```


## Data

Data used throughout the book is expected to be on the `data` directory. For instance, chapter #2 data looks like this:

```
» tree data
data
└── chapter-2
    ├── block_1.zip
    ├── block_10.zip
    ├── block_2.zip
    ├── block_3.zip
    ├── block_4.zip
    ├── block_5.zip
    ├── block_6.zip
    ├── block_7.zip
    ├── block_8.zip
    ├── block_9.zip
    ├── documentation
    ├── donation.zip
    ├── frequencies.csv
    └── linkage
        ├── block_1.csv
        ├── block_10.csv
        ├── block_2.csv
        ├── block_3.csv
        ├── block_4.csv
        ├── block_5.csv
        ├── block_6.csv
        ├── block_7.csv
        ├── block_8.csv
        └── block_9.csv
```


For several reasons, those files are kept out of this repo.