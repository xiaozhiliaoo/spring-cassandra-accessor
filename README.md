# 思路

1. 通过FactoryBean生成，而不是Accessor，但是需要在Accessor上加Component
2. Accessor+AccessorScan方式

先手动配置，后注解声明化处理。