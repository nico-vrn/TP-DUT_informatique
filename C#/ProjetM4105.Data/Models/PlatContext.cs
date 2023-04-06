using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity;
using ProjetM4105.Data.Models;

namespace ProjetM4105.Data.Models
{
    public class PlatContext : DbContext
    {
        public PlatContext() : base("ProjetM4105")
        { }
        public DbSet<Plats> Plats { get; set; }
        public object Dishes { get; internal set; }
        public DbSet<Cart> Carts { get; set; }
        public DbSet<Order> Orders { get; set; }
        public DbSet<OrderDetail> OrderDetails { get; set; }
    }
}
