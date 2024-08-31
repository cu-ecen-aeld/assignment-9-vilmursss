inherit update-rc.d

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-vilmursss.git;protocol=ssh;branch=master \
           file://S97aesdcharmodule"

PV = "1.0+git${SRCPV}"

SRCREV = "99f41fdd3ce6f76e29e3a81af0990cdd1b331b80"

S = "${WORKDIR}/git/aesd-char-driver"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S97aesdcharmodule"
INITSCRIPT_PARAMS = "defaults 97"

inherit module

# Add extra CFLAGS
CFLAGS = "-std=gnu17"


# Append to EXTRA_OEMAKE for the do_compile task
EXTRA_OEMAKE:append:task-compile = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR} EXTRA_CFLAGS='${CFLAGS}'"

FILES:${PN} += "${bindir}/aesdchar_load ${bindir}/aesdchar_unload ${sysconfdir}/init.d/S97aesdcharmodule"

do_configure() {
    :
}

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdchar_load ${D}${bindir}/aesdchar_load
    install -m 0755 ${S}/aesdchar_unload ${D}${bindir}/aesdchar_unload
    
    # Install the init script
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/S97aesdcharmodule ${D}${sysconfdir}/init.d/S97aesdcharmodule
    
    # Install device driver
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
}
