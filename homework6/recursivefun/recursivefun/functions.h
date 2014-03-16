#include <iostream>
#include <vector>
#include <cmath>
#include <cstdio>

struct Z
{
    static const long long arr = 1;

    static long long eval(std::vector < long long > a)
    {
        return 0;
    }
};

struct N
{
    static const long long arr = 1;

    static long long eval(std::vector < long long > a)
    {
        return a[0] + 1;
    }
};

template <  long long  n,  long long  k >
struct U
{
    static const long long arr = n;

    static long long eval(std::vector < long long > a)
    {
        return a[k - 1];
    }
};

template < class F, class g, class ... G >
struct S
{
    static const long long arr = g::arr;

    static long long eval(std::vector < long long > a)
    {
        return F::eval((std::vector < long long >) {g::eval(a), G::eval(a) ...});
    }
};

template <class F, class G>
struct R
{
    static const long long arr = F::arr + 1;

    static long long eval(std::vector < long long > a)
    {
        std::vector < long long > tmp(a.begin(), a.end() - 1);
        if (a.back())
        {
            tmp.push_back(a.back() - 1);
            tmp.push_back(R<F, G>::eval(tmp));
            return G::eval(tmp);
        }
        return F::eval(tmp);
    }
};

template <class F>
struct M
{
    static const long long arr = F::arr - 1;

    static long long eval(std::vector < long long >  a)
    {
        std::vector < long long > tmp = a;
        tmp.push_back(0);
        for (long long y = 0; F::eval(tmp); y++)
        {
            tmp[arr] = y;
        }
        return tmp[arr];
    }
};

struct get_sum
{
    static long long eval(std::vector < long long > a)
    {
        return a[0] + a[1];
    }
};

struct get_sub
{
    static long long eval(std::vector <long long> a)
    {
        if (a[0] >= a[1])
            return a[0] - a[1];
        return 0;
    }
};

struct get_mult
{
    static long long eval(std::vector < long long >  a)
    {
        return a[0] * a[1];
    }
};

struct get_pow
{
    static  long long eval(std::vector < long long >  a)
    {
        return (long long)pow((double)a[0], (double)a[1]);
    }
};

struct get_div
{
    static  long long eval(std::vector < long long >  a)
    {
        return a[0] / a[1];
    }
};

struct get_mod
{
    static  long long eval(std::vector < long long >  a)
    {
        return a[0] % a[1];
    }
};


struct get_plog
{
    static  long long eval(std::vector < long long >  a)
    {
        long long ans = 1;
         long long  ind = 0;
        while (a[1] % ans == 0)
        {
            ++ind;
            ans *= a[0];
        }
        return --ind;
    }
};

struct equals
{
    static long long eval(std::vector <long long>  a)
    {
        if (a[0] == a[1])
            return 1;
        else
            return 0;
    }
};


typedef S< R< Z, U< 3, 2 > >, U< 1, 1 >, U< 1, 1 > > DEC;

typedef S< get_sum, U<2, 1>, U<2, 2> > SUM;

typedef S< get_sub, U<2, 1>, U<2, 2> > SUB;

typedef S< get_mult, U<2, 1>, U<2, 2> > MULT ;

typedef S< get_pow, U<2, 1>, U<2, 2> > POW;

typedef S< get_div, U<2, 1>, U<2, 2> > DIV;

typedef S< get_mod, U<2, 1>, U<2, 2> > MOD;

typedef S < R< N, S< Z, U< 3, 3 > > >, U< 1, 1 >, U< 1, 1 > > NOT;

typedef S< S< NOT, NOT >, SUB > more;

typedef S < S< NOT, NOT >, S< SUB, U< 2, 2 >, U< 2, 1 > > > IS_LESS;

typedef S< equals, U<2, 1>, U<2, 2> > EQUALS;

typedef S< SUB, S< SUM, U< 3, 3 >, S< MULT , S< S < NOT, NOT >, U < 3, 1> >, U < 3, 2 > > >, S< MULT , S < S< NOT, NOT >, U< 3, 1> >, U< 3, 3 > > > IF;


typedef S< DEC, M< S< S< EQUALS, MOD, Z > , U< 3, 2 >, S< POW, U< 3, 1 >, U< 3, 3> > > > > PLOG;

typedef S< R< S< more, U< 1, 1 >, S < N, Z > >, S< S<  S< S< NOT, NOT >, MULT  > , S< MOD, U< 3, 1 >, U< 3, 2 > >, U< 3, 3 > >, U< 3, 1 >, S< S< N, N >, U< 3, 2 > >, U< 3, 3 > > >, U< 1, 1 >,
            S< DEC, S< DIV, U< 1, 1 >, S< N,S < N, Z >  > > > >  IS_PRIME;

typedef S< R< Z, S< SUM, S< IS_PRIME, U< 3, 2 > >, U< 3, 3 > > >, U< 1, 1 >, S< N, U< 1, 1 > > > PREV_PRIME;

typedef S< M< S< more, U< 2, 1 >, S< PREV_PRIME, U< 2, 2 > > > >, N > NTH_PRIME;


/////////////////////6.5/////////////////////////////////////////////////////

typedef S< DEC, S< PLOG,  S< N, S < N, Z >  > , U< 1, 1 > > > stack_length;

typedef S< MULT ,  S<  S< N, S < N, Z >  > , U<2, 1> > , S< MULT , U< 2, 1 >,
            S< POW, S< NTH_PRIME, S< N, S< stack_length, U< 2, 1 > > > >, S< N, U< 2, 2 > > > >  > push;

typedef S< push, S< push, U<3, 1>, U<3, 2> >, U<3, 3> >  push2;

typedef S< DEC, S< PLOG, S< NTH_PRIME, stack_length >, U< 1, 1 > > > get_first;

typedef S< DEC, S< PLOG, S< NTH_PRIME, S< DEC, stack_length > >, U< 1, 1 > > > get_first2;

typedef S< DIV, S< DIV, U< 1, 1 >, S< POW, S< NTH_PRIME,  stack_length >, S< N, get_first > > >,  S< N, S < N, Z >  >  > pop;

typedef S< pop, S< pop, U<1, 1> > > poptwo;

typedef S< IF, S< EQUALS, get_first, Z >, S< push2, poptwo, S< DEC, get_first2 >, S < N, Z > >,
        S< S< push, S< push, S< push, U<4, 1>, U<4, 2> >, U<4, 3> >, U<4, 4> > , poptwo, S< DEC, get_first2 >, get_first2, S< DEC, get_first> > > another;
typedef R< S< push2,  S< N, S < N, Z >  > , U<2, 1>, U<2, 2> >, S< S< IF, S< EQUALS, get_first2, Z >,
        S< push, poptwo, S< N, get_first> >, another >, U<4,4> > > execute;
typedef M< S< SUB, S< S< stack_length, S< execute, U<3, 1>, U<3, 2>, U<3, 3> > >, U<3, 1>, U<3, 2>, U<3, 3> >, S < N, Z >  > > get_steps_num;
typedef S< get_first, S< execute, U<3,1>, U<3,2>, S< get_steps_num, U<3, 1>, U<3, 2> > > > ackerman;

template<class G, class... K>
void print_out(K... x)
{
    std::vector<long long> a = { x... };
    std::cout << G::eval(a) << std::endl;
}
