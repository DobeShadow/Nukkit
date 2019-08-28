package cn.nukkit.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static cn.nukkit.api.API.Definition.UNIVERSAL;
import static cn.nukkit.api.API.Usage.BLEEDING;

/**
 * 描述API元素.
 *
 * @author Lin Mulan, Nukkit Project
 * @see Usage
 * @see Definition
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@API(usage = BLEEDING, definition = UNIVERSAL)
@SuppressWarnings("unused")
public @interface API {

    /**
     * 指示API元素的稳定性级别.
     * 稳定性还描述了何时使用此API元素.
     *
     * @return The stability
     * @see Usage
     */
    Usage usage();

    /**
     * 表示此API元素支持的定义或平台.
     *
     * @return The definition
     * @see Definition
     */
    Definition definition();

    /**
     * 枚举常量用于API使用. 指示何时使用此API元素.
     *
     * @see #DEPRECATED
     * @see #INCUBATING
     * @see #BLEEDING
     * @see #EXPERIMENTAL
     * @see #MAINTAINED
     * @see #STABLE
     */
    enum Usage {

        /**
         * 如果不再使用,可能会在下一个次要版本中消失.
         */
        DEPRECATED,

        /**
         * 用于草稿中的功能. 只应用于测试.
         *
         * <p>可能包含值得注意的新功能,在重新标记之前会被移动到新的包中 {@link #BLEEDING}.
         * 可能不安全,可能会在没有事先通知的情况下被移除. 如果使用,将发送警告.
         */
        INCUBATING,

        /**
         * 用于早期开发中的功能. 只应用于测试.
         *
         * <p>可能是未包装,不安全或有未选中的参数.
         * 要求进一步作出贡献,以加强,加强或简化,然后再向 {@link #EXPERIMENTAL}.
         * 可在不事先通知的情况下删除或修改.
         */
        BLEEDING,

        /**
         * 用于我们正在寻找反馈的新的实验性功能.
         * 至少稳定的发展.
         *
         * <p>谨慎使用,以后可能会对 {@link #MAINTAINED} 或 {@link #STABLE} 进行注释.
         * 但也可能会被删除,恕不另行通知.
         */
        EXPERIMENTAL,

        /**
         * 用于测试,记录和至少稳定用于生产的功能.
         *
         * <p>对于至少下一次次要发布,这些功能不会以向后兼容的方式进行修改
         * 目前的重要版本. 将首先标记为 {@link #DEPRECATED} 为已弃用.
         */
        MAINTAINED,

        /**
         * 用于测试,记录和生产使用中首选的功能.
         *
         * <p>在当前版本中不会以向后兼容的方式进行更改.
         */
        STABLE
    }

    /**
     * 枚举常量用于API定义. 指示此API元素支持的客户端平台.
     *
     * @see #INTERNAL
     * @see #PLATFORM_NATIVE
     * @see #UNIVERSAL
     */
    enum Definition {

        /**
         * 仅用于Nukkit本身的功能.
         * 不应该用于生产.
         */
        INTERNAL,

        /**
         * 仅适用于一个或多个客户端平台上提供的功能.
         *
         * <p>通过使用 {@code PLATFORM_NATIVE} 功能, 程序将失去一些提供的跨平台功能.
         * 某些客户端平台可能无法使用. 在使用此API元素之前,请仔细阅读文档.
         */
        PLATFORM_NATIVE,

        /**
         * 用于在所有客户端平台中实现的功能.
         *
         * <p>首选用于生产用途，但有时缺乏平台原生功能.
         */
        UNIVERSAL
    }
}
