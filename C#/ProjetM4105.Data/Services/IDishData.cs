using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ProjetM4105.Data.Models;


namespace ProjetM4105.Data.Services
{
    public interface IDishData
    {
        IEnumerable<Plats> GetAllDishes();
        void AddDish(Plats dish);

        List<Plats> GetDishesByCategory(Category Type);
    }
}
