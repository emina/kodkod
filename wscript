#! /usr/bin/env python
# encoding: utf-8
import os.path

def options(opt):
    opt.load('java')
    opt.recurse('jni')

def configure(conf):
    conf.load('java')
    conf.recurse('jni')

def build(bld):
    bld.recurse('jni')

    bld(rule = 'wget http://download.forge.ow2.org/sat4j/${TGT}',
        target = 'sat4j-core-v20130525.zip')
    bld(rule = 'unzip ${SRC} -x *src.jar',
        source = 'sat4j-core-v20130525.zip',
        target = 'org.sat4j.core.jar')

    bld.add_group()

    bld(features  = 'javac jar',
        name      = 'kodkod',
        srcdir    = 'src', 
        outdir    = 'kodkod',
        compat    = '1.8',
        classpath = ['.', 'org.sat4j.core.jar'],
        manifest  = 'src/MANIFEST',
        basedir   = 'kodkod',
        destfile  = 'kodkod.jar')
    
    bld(features  = 'javac jar',
        name      = 'examples',
        use       = 'kodkod',
        srcdir    = 'examples', 
        outdir    = 'examples',
        compat    = '1.8',
        classpath = ['.', 'kodkod.jar'],
        manifest  = 'examples/MANIFEST',
        basedir   = 'examples',
        destfile  = 'examples.jar')
    
    bld.install_files('${LIBDIR}', ['kodkod.jar', 'examples.jar'])

def distclean(ctx):
    from waflib import Scripting
    Scripting.distclean(ctx)
    ctx.recurse('jni')
    

