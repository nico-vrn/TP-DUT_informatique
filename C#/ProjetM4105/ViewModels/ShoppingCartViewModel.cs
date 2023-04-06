using ProjetM4105.Models;
using ProjetM4105.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ProjetM4105.ViewModels
{
    public class ShoppingCartViewModel
    {
        public List<Cart> CartItems { get; set; }
        public decimal CartTotal { get; set; }
    }
}