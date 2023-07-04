module Main where
import Data.Char
import Data.List
import System.Environment
import Control.Monad

-- a pair in the comparison netwrok
type Pair = (Int, Int)

-- a comparison network in the form [(int,int)]
type ComparisonNetwork = [Pair]

-- | reads a file containing a comparator network.
parseNetwork :: String -> IO [Pair]
parseNetwork filename = do
  contents <- readFile filename
  let network = read contents :: [Pair]
  return network

--PART1-------------------------------------------------------------------------
-- Takes a comparison pair and returns a formatted string of the comparison
formatPair :: Pair -> String
formatPair (x, y) = show x ++ " -- " ++ show y

--PART2---------------------------------------------------------------------------
-- takes a comparison network and applies it to a given list
applyNetwork :: ComparisonNetwork -> [Int] -> [Int]
applyNetwork network xs = foldr (applyComparison network) xs (reverse network)

-- checks if digits at indices (a,b) if list[a] > list[b] if they are they will be swapped
applyComparison :: ComparisonNetwork -> Pair -> [Int] -> [Int]
applyComparison network (i, j) xs
  | xs !! (i-1) <= xs !! (j-1) = xs
  | otherwise = swap i j xs

-- swaps digits in a list
swap :: Int -> Int -> [a] -> [a]
swap i j xs = let
    ith = xs !! (i-1)
    jth = xs !! (j-1)
    in take (i-1) xs ++ [jth] ++ drop i (take (j-1) xs ++ [ith] ++ drop j xs)

--PART3------------------------------------------------------------------------
-- | Given a list of pairs, return a list of lists of parallel pairs.
toParallel :: ComparisonNetwork -> [ComparisonNetwork]
toParallel pairs = reverse (foldl go [] pairs)
  where
    go [] pair = [[pair]]
    go (level:levels) pair =
      if conflicts pair level
        then [pair] : level : levels
        else (pair : level) : levels

-- | Determine if a pair conflicts with any pair in a given list of pairs.
conflicts :: Pair -> ComparisonNetwork -> Bool
conflicts pair pairs = any (sharesWire pair) pairs
  where
    sharesWire (a, b) (c, d) = a == c || a == d || b == c || b == d

-- formats the comparison network by seperating each level that can compared in parallel
formatParallel :: [[Pair]] -> String
formatParallel parallel = unlines (map formatLevel parallel)
  where
    formatLevel level = intercalate " , " (map formatPair (sortOn fst level))
  

--PART4----------------------------------------------------------------------------------
-- | Find the largest integer in a list of tuples of integers. [(5,1),(7,2),(4,9)] --> 9
networkMax :: ComparisonNetwork -> Int
networkMax network = maximum $ concatMap (\(x, y) -> [x, y]) network

  -- | Generate all possible input sequences of length n consisting of 0s and 1s.
generateSequences :: Int -> [[Int]]
generateSequences n = sequence (replicate n [0,1])

-- | Test whether a comparator network is a sorting network.
isSortingNetwork :: ComparisonNetwork -> Bool
isSortingNetwork network = all (\xs -> applyNetwork network xs == sort xs) (generateSequences $ networkMax network)

--COMMAND LINE ARGUMENTS--------------------------------------------------------------------
main :: IO ()
main =
  do
    args <- getArgs
    case args of   

      --when user wants to visualize comparison network: run haskell Main.hs Read filename
      "Read":filename:_ ->
        do
          input <- parseNetwork filename
          let network =  map formatPair input
          let output = init $ unlines network
          writeFile "network.txt" output

      --when user wants to sort a list with a comparison network: run haskell Main.hs Run filename Run "[Int]"
      "Run":filename:sequenceStr:_ ->
        do
          let sequence = read (sequenceStr) :: [Int]
          network <- parseNetwork filename
          let result = applyNetwork network sequence
          putStrLn (show result)

      --when user wants to visualize parallel levels in comparison network: run haskell Main.hs Parallel filename
      "Parallel":filename:_ ->
        do
          network <- parseNetwork filename
          let parallel = toParallel network
          let output = formatParallel parallel
          writeFile "parallel.txt" output
      
      --when user wants to check if comparison network is sorting network: run haskell Main.hs Sorting filename
      "Sorting":filename:_ ->
        do
          network <- parseNetwork filename
          putStrLn (show $ isSortingNetwork network)

      _ -> fail "Invalid command-line argument\nCommands are:\nRead filename\nRun filename \"[Int]\"\nParallel filename\nSorting filename\n"