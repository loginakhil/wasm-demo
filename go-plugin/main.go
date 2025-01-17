package main

//export RoundPrices
func RoundPrices(globalEntityId string, unitPrice float64, quantity float64) float64 {
  // Rounding logic goes here
  switch globalEntityId {
    case "TB_BH":
      return unitPrice * quantity * 100; // custom logic
    default:
      return unitPrice * quantity * 10
  }
}


func main() {}
