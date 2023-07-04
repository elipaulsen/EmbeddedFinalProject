# Comparator Network
an executable program that can be
invoked from a terminal using runhaskell Main.hs
#

A sorting network is a comparator network where the final values on the wires are indeed always
sorted (smaller numbers towards the top), no matter what initial values we start with.
The zero-one principle discussed in the CLR chapter states that it is enough to make sure a
comparator network with n wires always correct sorts lists of 0s and 1s. The more general case,
where it sorts lists of numbers 1 through n, follows from correctly sorting lists of 0s and 1s.
For this problem,this program will test whether or not a comparator network is a sorting
network.

```
runhaskell Main.hs Sorting <filename>
```