--Q1
select NS, NH,  CapCH
from RESORTS
where TypeS = 'MONTAGNE';

--Q2
select h.NS, h.NH, h.AdrH, h.TelH, h.CatH
from HOTELS h, RESORTS r
where (r.NS = h.nS and r.TypeS = 'mer');

--Q3
select r.NomS
from RESORTS r, HOTELS h
where (r.TypeS = 'mer' and r.NS = h.NS and CatH=4);

--Q4
select distinct g.NomCl, g.AdrCl
from GUESTS g, BOOKINGS b, RESORTS r
where (b.NCl=g.NCL and r.NS=b.NS and r.TypeS='montagne');

--Q5
select *
from ROOMS ro, HOTELS h, RESORTS re
where (h.NS = re.NS and h.NS = ro.NS and
  h.NH = ro.NH and re.typeS = 'montagne' and h.CatH = 2 and ro.Prix < 50);

--Q6
select distinct g.NomCl
from BOOKINGS b, GUESTS g, ROOMS ro, RESORTS rs
where (rs.NS = ro.NS
  and ro.NS = b.NS and ro.NH=b.NH
  and ro.NCH = b.NCH
  and b.NCL = g.NCL
  and rs.TypeS = 'mer'
  and ro.TypCh in ('D', 'DWC'));

--Q7
select distinct g.NomCl
from GUESTS g, HOTELS h
where (g.AdrCl = h.AdrH);

--Q8 : version avec différence ensembliste
select h.NS, h.NH
from HOTELS h
where h.CatH = 4
minus
select ro.NS, ro.NH
from ROOMS ro
where ro.TypCh <> 'SDB'; --Complémentaire : Tous les hotels ayant au moins une chambre sans SDB

--Q8 : autre version avec not in
select h.NS, h.NH
from HOTELS h
where h.CatH = 4
  and (h.NS, h.NH) not in (
    select ro.NS, ro.NH
    from ROOMS ro
    where ro.TypCh != 'SDB'
  );

--Q9 : hotels avec noms, adresse, catégorie ayant au moins 2 chambres de même prix

select distinct h.NomH, h.AdrH, h.CatH
from HOTELS h, ROOMS ro1, ROOMS ro2
where (h.NS = ro1.NS and h.NH = ro1.NH
  and ro1.NS = ro2.NS and ro1.NH = ro2.NH
  and ro1.NCH <> ro2.NCH
  and ro1.prix = ro2.prix);

--Q9 autre solution
select distinct h.NS, h.NH, h.NomH, h.catH, h.AdrH
from ROOMS ro, HOTELS h
where h.NS = ro.NS and h.NH = ro.NH
group by h.NS, h.NH, ro.prix, h.NomH, h.CatH, h.AdrH
having count(*) >= 2;

--Q10 : Liste des hôtels avec leur nom, adresse et catégorie, et leur nombre
--de réservations dans l’année
select distinct h.NS, h.NH, h.NomH, h.adrH, h.CatH, count(*) as NbResa
from HOTELS h, BOOKINGS b
where b.NH = h.NH and b.NS = h.NS
group by h.NS, h.NH, h.NomH, h.adrH, h.CatH
--si on s'arrête là il manque les hôtels avec aucune réservation
union
(select NS, NH, NomH, adrH, CatH, 0 as NbResa
from HOTELS
where (ns, nh) not in (select distinct ns, nh from bookings)
);

--Q11 : Adresse de l’hôtel de la station « Chamonix » ayant eu le plus de
--réservations faites sur l’année
select h.adrH
from HOTELS h, RESORTS r, BOOKINGS b
where h.NS = r.NS
and h.NS = b.NS
and r.NomS = 'Chamonix'
group by h.NS, h.NH, h.NomH, h.adrH
having count (b.NCl) =
  (select max(count(ncl))
  from BOOKINGS b, RESORTS r
  where h.NS = r.NS and r.NomS = 'Chamonix'
  group by b.NS, b.NH);

--Q12
select b.jour
from BOOKINGS b, RESORTS r, HOTELS h
where r.NS = h.NS and h.NS = b.NS and h.NH = b.NH and r.NomS = 'Chamonix'
and h.NomH = 'Bon Séjour'
group by b.jour
having count(*) = (
  select max(count(*))
  from BOOKINGS b, RESORTS r, HOTELS h
  where r.NS = h.NS
  and h.NS = r.NS
  and h.NS = b.NS
  and h.NH = b.NH
  and r.NomS = 'Chamonix'
  and h.NomH = 'Bon Séjour'
  group by b.jour
);

--Q13
select h.NS, h.NH, h.NomH, h.AdrH, h.CatH
from HOTELS h, ROOMS r
where h.NS = r.NS and h.NH = r.NH
group by h.NS, h.NH, h.NomH, h.AdrH, h.CatH
having max(r.prix)<40;

--Q14
select min(ro.prix) as prix
from RESORTS re, HOTELS h, ROOMS ro
where re.NS = h.NS and h.NS = ro.NS and h.NH = ro.NH and h.CatH = 3
and re.TypeS = 'mer';

--Q15
select g.NCl, g.NomCl
from GUESTS g, BOOKINGS b, HOTELS h, RESORTS r
where g.Ncl = b.NCl
and h.NS = r.NS
and h.NS = r.NS
and h.NS = b.NS
and h.NH = b.NH
and r.NomS = 'Chamonix'
and h.CatH = 2
group by g.NCl, g.NomCl
having count(distinct h.NH) = (
  select count(*)
  from HOTELS h, RESORTS r
  where h.NS = r.NS
  and r.NomS = 'Chamonix'
  and h.CatH = 2
);

--Q16
select distinct g.NCl, g.NomCl
from GUESTS g, BOOKINGS b1, BOOKINGS b2, BOOKINGS b3
where g.NCl = b1.NCl
and g.NCL = b2.NCl
and g.NCL = b3.NCl
and b1.NS = b2.NS
and b1.NS = b3.NS
and b1.NH = b2.NH
and b2.NH = b3.NH
and b1.NCH = b2.NCH
and b1.NCH = b3.NCH
and b1.jour = b2.jour - 1
and b1.jour = b3.jour -2;
