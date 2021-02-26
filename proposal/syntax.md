## Variable Definitions
```c
// a mutable variable defined to the type's "zero" value, in this case an empty string
var myMutableVariable string
// a mutable variabel with type inference
var myStringVariable = "string"

// an immutable variable with type inference
const PI = 3.14159265
```

## Assignment
```c
// array destructuring
var arr = ['a', 'b', 'c']
var a, b, c = arr

// iterator destructuring with optional "rest" variable
var list = [0, 1, 2, 3, 4, 5, 6, 7]
var first, second, rest = list
// first = 0
// second = 1
// rest = [2, 3, 4, 5, 6, 7]

```



## Loops

```c
// normal loop a la for loop
loop var i = 0; i < 10; i++ {
    log(i)
}
// foreach loop over anything implementing Iterator. Optional index variable..
loop var item, idx in list {
    log(item, idx)
}
// loop forever
loop {
    log(“Stdout overflow imminent”)
}
```


## If-else statements
```c
if cond {
    log(“branch 0”)
} else if 4 < x {
    log(“branch 1”)
} else {
    log(“branch 2”)
}
```
