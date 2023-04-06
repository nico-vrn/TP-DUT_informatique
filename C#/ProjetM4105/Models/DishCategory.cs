using ProjetM4105.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ProjetM4105.Models
{
    public class DishCategory
    {
        public Category Type { get; set; }

        public DishCategory(Category type, string name, List<Plats> dishes)
        {
            Type = type;
            Name = name;
            Dishes = dishes;
        }

        public String Name { get; set; }
        public String PlatID { get; set; }

        public List<Plats> Dishes { get; set; }
    }

}