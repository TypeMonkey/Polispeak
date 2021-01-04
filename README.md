<img src="https://raw.githubusercontent.com/TypeMonkey/Polispeak/master/polispeak.png" width="350">

###  ***Write code that looks like legislation***

## Concepts
A program in Polispeak is called **Legislation**, which are composed of **Definitions** and **Sections**.

With respect to programming concepts:
- **Definitions** *are analogous to* ***types*** 
- **Sections** *are analogous to* ***functions***

When legislation is *yet to be executed*, we say that its being __drafted.__  It is during the drafting stage that legislation is check for *coherency*  - **i.e:** ***structure*** - and *sensability* - ***type correctness*** .

Once legislation passes the drafting phrase, it is ready to to be **enforced**. 
In other words, the corresponding Polispeak program is ready to be executed.

### Sections
As said, **sections** are analogous to ***functions*** as we encounter in programming. Like functions, sections:

- May expect **provisions** ( *function parameters*  ).
- May result in a **fulfillment** ( *The return value of a function*  )

Also like functions, sections can be **invoked**.

### Definitions
In programming, to define a custom datatype, we may have to declare `struct` , `class` , `record` , or use some other facility. **Definitions** are essentially structs/records, composed of named, immutable **members / components**. 

Definitions can be instantiated, as well as dereferenced like their programming counterparts.

### Precedents 
There will be certain procedures that cannot be directly described in just Polispeak alone. In such case, legislation requiring such procedures will need to draw upon some **precedent**.

With respect to programming, **precedent** is Java code that defines types ( *definitions* ) and functions ( *sections* ) that a Polispeak program can interact with.

The Polispeak Interpreter currently has two built-in precedents:
- **The Constitution** , which defines built-in types such as Integers, Floats and Strings, as well as helpful sections regarding arithmetic, reflection and etc.
- **The Bill of Strings**, which has essential sections for procedures in manipulating Strings.

## Motivation
As a kid, I loved playing nation simulation games - notably [Cybernations](https://www.cybernations.net/default.asp). The thing is, these games often lacked an ability for players to define custom policy to enact for their nations - which is completly understandable. 

After gaining a stable foothold in programming, the first thing I worked on was my own simulation game to address this exact issue. However, I found it too difficult and forgot about it until now. 

Coupled with the insanity and boredom - as well as my increased exposure to *certain legislative workings* ( ***Ahem*** stimulus) - I initially began this project to parody
the harmful and disastorous attempts of **clueless** legislators to pass legislation on an industry - like **anything technological** - with little to no knowledge about it.

I mean, look at [SOPA](https://en.wikipedia.org/wiki/Stop_Online_Piracy_Act), 
[CISA](https://en.wikipedia.org/wiki/Cybersecurity_Information_Sharing_Act), 
[PIPA](https://en.wikipedia.org/wiki/PROTECT_IP_Act), 
[CACA](https://en.wikipedia.org/wiki/Ajit_Pai), 
[DOODOO](https://i.imgur.com/rT5RRtY.jpg) 
*and the many varying attempts that are **in the agendas of many legislators***. 
Either from a lack of understanding, ***or frequent visitation of expensive lobbies***, these people almost always end up pushing policy with great consequences that is **of their benefit** and **at our cost**.
