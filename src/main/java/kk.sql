create database ko;
create table k
(
    id int
);
insert into k(id)
           values (9);
select *
from k;


select hh.sid ,hh.sname from (select st.sid,st.sname,s.cid  from student st join sc s on st.sid=s.sid) hh
    where hh.cid not in
 (select c.cid from teacher t join course c on t.tid=c.tid where t.tname='张三');

select * from (select * from student st join sc ss on st.sid= ss.sid) stt where stt.cid not in
(select s.cid from course c join sc s on c.cid=s.cid where s.sid='01');



select s.cid,count(*)  over(partition by s.cid) kl from sc s order by kl desc,s.cid asc ;


select * from (select *,dense_rank() over(partition by s.cid order by s.score desc ) rd from sc s)j where j.rd <3 ;

select cid from sc where score<60 order by cid desc ;

select cid,avg(score) d from sc group by cid order by d asc,cid desc;
select * from student where substring(string(sage),0,4)='1990'

select * from student s from where s.(select * from student) as k
select * from student where sname like '%风%'
select sname,count(*) r from student group by sname having r>=2

select ssex,count(*) from student group by ssex;
 select * from student where sid in (select sid from sc group by sid having count(*)=1);



select st.sid from student st join sc s on st.sid=s.sid group by st.sid having  count(*)>=2;

select cid,count(*) from sc group by cid

select sid ,sum(score) s from sc group by sid order by s desc
select * ,avg(score) over (partition by sid ) r from sc order by r desc;
select t.tid,avg(ss.score) from teacher t join course c on t.tid=c.tid join sc ss on c.cid=ss.cid group by t.tid

select hh.sid,hh.sname from (select st.sid,st.sname,s.cid  from student st join sc s on st.sid=s.sid) hh
where hh.cid  in
      (select c.cid from teacher t join course c on t.tid=c.tid where t.tname='张三') group by hh.sid,hh.sname having hh.sid not in (select sid from student);



select collect_set(s.cid) tr from student st join sc s on st.sid=s.sid where s.sid !='01'


select * from (select collect_set(s.cid) tr from student st join sc s on st.sid=s.sid where s.sid !='01') kl where kl.tr
                                                                                                in (

 select collect_set(s.cid) cd from course c join sc s on c.cid=s.cid where s.sid='01')

select c.cid,c.cname ,max(s.score) ,min(s.score) ,round(avg(s.score),2)  from sc s left join course c on s.cid=c.cid group by c.cid, c.cname;

select sum(score) from student st   join sc  s on st.sid=s.sid

select * from teacher t join course c on t.tid=c.tid join (select s.cid,round(avg(score),2) from sc s group by s.cid) k on k.cid=c.cid









select * from student sst join (select st.sid ,count(1) summ from student st  left join sc s on st.sid=s.sid group by st.sid  having summ<3) h on sst.sid= h.sid























select * ,avg(score) over (partition by sid ) r from sc order by r desc;
select sname,ssex,count(sname) r from student group by sname, ssex  having r>=2

select s.cid,count(*)  kl from sc s  group by s.cid order by kl desc,s.cid asc ;












