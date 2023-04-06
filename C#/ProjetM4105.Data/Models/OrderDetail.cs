using ProjetM4105.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ProjetM4105.Data.Models
{
    public class OrderDetail
    {
        public int OrderDetailId { get; set; }
        public int OrderID { get; set; }
        public int PlatID { get; set; }
        public int Quantity { get; set; }
        public decimal UnitPrice { get; set; }
        public virtual Plats Plats { get; set; }
        public virtual Order Order { get; set; }
    }
}