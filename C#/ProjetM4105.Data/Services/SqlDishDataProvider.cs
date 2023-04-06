using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ProjetM4105.Data.Services;
using ProjetM4105.Data.Models;


namespace ProjetM4105.Data.Services
{
    public class SqlDishDataProvider : IDishData
    {
        private readonly PlatContext _dbContext;

        public SqlDishDataProvider(PlatContext context)
        {
            this._dbContext = context;
        }

        public void AddDish(Plats dish)
        {
            this._dbContext.Plats.Add(dish);
            this._dbContext.SaveChanges();
        }
        
        public IEnumerable<Plats> GetAllDishes()
        {
            return this._dbContext.Plats.OrderBy(d => d.PlatName).ToList();
        }

        public List<Plats> GetDishesByCategory(Category Type)
        {
            return this._dbContext.Plats.Where(d => d.Category == Type).ToList();
        }
    }
}
