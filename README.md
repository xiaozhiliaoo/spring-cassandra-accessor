# 思路

1. 第一步：AccessorFactoryBean（手动配置）
2. 第二步：AccessorScannerConfigurer（手动配置）
3. 第三步：AccessorScannerRegistrar（注解配置）

从外部项目看，手动配置需要全部自己配置，也就是第二步自己组装Bean。但是如果第三步，全部外部导入。就会自动实例化Bean。
实际上所见的大项目，大多在第二步和第三步之间。这些也就是SpringBoot出现的自动装配契机。